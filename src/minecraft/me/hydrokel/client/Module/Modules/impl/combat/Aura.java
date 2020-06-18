package me.hydrokel.client.Module.Modules.impl.combat;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.Event2D;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.CombatUtils;
import me.hydrokel.client.Utils.RainbowUtils;
import me.hydrokel.client.Utils.RayCastUtil;
import me.hydrokel.client.Utils.TimeHelper;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Aura extends Module {
	
	private int tick = 0;
	 private TimeHelper targetChangeTimer = new TimeHelper();
	 private TimeHelper cpsTimer = new TimeHelper();
	 private float[] facing;
	
	public Aura() {
		super("Aura", Keyboard.KEY_V, Category.COMBAT);
	}

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Aura", "You toggled Aura.", 1));

        super.onEnable();

    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Aura", "You untoggled Aura.", 1));
        super.onDisable();

    }

    @Override
    public void setup(){
    	ArrayList<String> options = new ArrayList<String>();
    	options.add("Normal");

    	Main.instance.setmgr.rSetting(new Setting("Aura Mode", this, "Normal", options));
    	Main.instance.setmgr.rSetting(new Setting("CPS", this, 8, 1, 20, true));
    	Main.instance.setmgr.rSetting(new Setting("REACH", this, 4.2, 3, 6, false));
    	Main.instance.setmgr.rSetting(new Setting("AutoBlock", this, false));
    	Main.instance.setmgr.rSetting(new Setting("Raycast", this, false));
    	Main.instance.setmgr.rSetting(new Setting("Info", this, true));
    }
	
	@EventTarget
	public void onMotion(EventMotion e) {
		switch(e.getState()) {
    	case PRE:
		if(Main.instance.setmgr.getSettingByName("Aura Mode").getValString().equalsIgnoreCase("Normal")){
			
		EntityLivingBase target = CombatUtils.findEntity();
		target = CombatUtils.getBestEntityByDistance();
		if(mc.thePlayer.getDistanceToEntity(target) <= Main.instance.setmgr.getSettingByName("REACH").getValDouble()) {
			  if(CombatUtils.isValid(target))
				  			  
			if(target == null)
	    		return;

			// System.out.println(target.ticksExisted);
			
			  facing = CombatUtils.getEntityRotations(mc.thePlayer, target);
	          e.setYaw(facing[0]);
	          e.setPitch(facing[1]);
			  //mc.thePlayer.rotationYawHead = facing[0];
	          //mc.thePlayer.rotationYaw = facing[0];
	          //mc.thePlayer.rotationPitch = facing[1];
            
            if ((Main.instance.setmgr.getSettingByName("AutoBlock").getValBoolean() && (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) || mc.thePlayer.isBlocking()) {
	            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
	        }
            
            Entity rayCastEntity = Main.instance.setmgr.getSettingByName("Raycast").getValBoolean() ? RayCastUtil.rayCast(Main.instance.setmgr.getSettingByName("Reach").getValDouble() + 1.0f, facing[0], facing[1]) : null;
            
            this.tick += 1;
		    if (this.tick >= 20.0D / Main.instance.setmgr.getSettingByName("CPS").getValDouble()) {    	
		    	//no Raycast
		    	//CombatUtils.attack(target);		    	
                mc.thePlayer.swingItem();
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(rayCastEntity == null ? target : rayCastEntity, C02PacketUseEntity.Action.ATTACK));
                //mc.playerController.attackEntity(mc.thePlayer, target);
		    	this.tick = 0;	
		    	break;
		    }
		    
            }
		}
		default:
			break;	
		}
	}
	
    @EventTarget
	public void onRender(Event2D event) {

        EntityLivingBase target = CombatUtils.findEntity();
        target = CombatUtils.getBestEntityByDistance();

        assert target != null;
        //mc.fontRendererObj.drawStringWithShadow(String.valueOf(target.ticksExisted), 3, 100, 0xFFFFFFFF);
        //mc.fontRendererObj.drawStringWithShadow(String.valueOf(mc.thePlayer.ticksExisted), 3, 110, 0xFFFFFFFF);


        if(Main.instance.setmgr.getSettingByName("Info").getValBoolean()) {
   		EntityLivingBase entity = CombatUtils.findEntity();
   		if(CombatUtils.isInReach(entity, Main.instance.setmgr.getSettingByName("Reach").getValDouble())) {
   			//ENTITY ON SCRENN
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            drawEntityOnScreen(368, 220, 14, 0F, 0F, entity);
            //DRAWRECT
   			Gui.drawRect(+445, 183, 350, 236, 0xA9000000);
   			mc.fontRendererObj.drawStringWithShadow("Heal:", 388, 194, 0xFFFFFFFF);
   			mc.fontRendererObj.drawStringWithShadow(Math.ceil(entity.getHealth()) + "", 415, 194, RainbowUtils.effect(1, 0.5F, 1).getRGB());
   			//NAME
   			mc.fontRendererObj.drawStringWithShadow(entity.getName() + "", 352, 224, 0xFFFFFFFF);		
   			NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(entity.getUniqueID());
   			//PING
            String ping = (networkPlayerInfo == null) ? "0ms" : (networkPlayerInfo.getResponseTime() + "ms");
            mc.fontRendererObj.drawStringWithShadow("Ping:", 388, 210, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow(ping + "", 414, 210, RainbowUtils.effect(1, 0.5F, 1).getRGB());
               
   		}
   	 }
   	 
   	}
    
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
		
}