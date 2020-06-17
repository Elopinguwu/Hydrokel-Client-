package me.hydrokel.client.Module.Modules.impl.render;

import java.awt.Color;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.Event3D;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.RenderUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PlayerESP extends Module {

	public PlayerESP() {
		super("PlayerESP", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	public void onDisable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "PlayerESP", "You untoggled PlayerESP.", 1));
		super.onDisable();
	}
	
	public void onEnable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "PlayerESP", "You toggled PlayerESP.", 1));
		super.onEnable();
	}
	
	@EventTarget
	public void onUpdate(Event3D e) {
		for(Object o : mc.theWorld.loadedEntityList) {
			if(o instanceof EntityLivingBase) {
				if(o != null && o != mc.thePlayer) {
					if(isValid((EntityLivingBase)o)) {
						render2Desp((EntityLivingBase)o, Color.pink.getRGB(), e.getPartialTicks());
					}
				}
			}
		}
	}
	
	public void render2Desp(EntityLivingBase e, int color, float partialTicks) {
		RenderUtils.setupFor2D(e, partialTicks);
		
		Gui.drawRect(31.0D, -85.0D, 26.0D, 11.0D, -16777216);
		Gui.drawRect(-31.0D, -79.0D, -26.0D, 11.0D, -16777216);
		Gui.drawRect(-30.0D, 6.0D, 30.0D, 11.0D, -16777216);
		Gui.drawRect(-30.0D, -79.0D, 30.0D, -74.0D, -16777216);
		//Gui.drawRect (-35.0D, -120.0D, 65.0D, 60.0D, color);


		Gui.drawRect(30.0D, -75.0D, 27.0D, 10.0D, color);
		Gui.drawRect(-30.0D, -75.0D, -27.0D, 10.0D, color);
		Gui.drawRect(-30.0D, 7.0D, 30.0D, 10.0D, color);
		Gui.drawRect(-30.0D, -78.0D, 30.0D, -75.0D, color);
		//RenderUtils.drawImage(new ResourceLocation("textures/LaVache.png"), -35, -120, 65, 60);
		
		RenderUtils.disableFor2D();
	}
	
	public boolean isValid(EntityLivingBase e) {
			if(e instanceof EntityPlayer)
				return true;
			//if(e instanceof EntityMob)
		//		return false;
		//	if(e instanceof EntityAnimal)
		//		return false;
		//	if(e.isInvisible())
		//		return false;
		return true;
	}

}
