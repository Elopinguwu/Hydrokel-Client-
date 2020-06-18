package me.hydrokel.client.Module.Modules.impl.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.Event2D;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.RainbowUtils;
import net.minecraft.client.gui.ScaledResolution;

public class HUD extends Module {

    public HUD() {
        super("HUD", Keyboard.KEY_P, Category.RENDER);

    }

	@Override
	public void onDisable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "HUD", "You untoggled HUD.", 1));
		super.onDisable();
	}

	@Override
	public void onEnable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "HUD", "You toggled HUD.", 1));
		super.onEnable();

	}

    @EventTarget
    private void onRender(Event2D event) {
    	//--------------------//
		GL11.glPushMatrix();
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		mc.fontRendererObj.drawStringWithShadow(Main.name + " "+ Main.version, 3, 2,
				RainbowUtils.effect(1, 0.5F, 5).getRGB());
		mc.fontRendererObj.drawStringWithShadow("By "+Main.creators, 3, 13, RainbowUtils.effect(1, 0.5F, 5).getRGB());

		//mc.fontRendererObj.drawStringWithShadow("Client", 52, 2, 0xffff00ff);
		GL11.glPopMatrix();
    	//--------------------//
		
    	mc.fontRendererObj.drawString("", 0, 0, 0XFFFFFFF, true);
    	int index = 0;
        int yCount = 1;
        ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);     	
        
        ArrayList<Module> mods = Main.instance.moduleManager.getModules();
        
        Collections.sort(Main.instance.moduleManager.getModules(), new Comparator<Module>() {
			public int compare(Module m1, Module m2) {
				if(Minecraft.fontRendererObj.getStringWidth(m1.getDisplayName()) > Minecraft.fontRendererObj.getStringWidth(m2.getDisplayName())) {
					return -1;
				}
				if(Minecraft.fontRendererObj.getStringWidth(m1.getDisplayName()) < Minecraft.fontRendererObj.getStringWidth(m2.getDisplayName())) {
					return 1;
				}
				return 0;
			}
		}); 
        for (Module m : mods) { 

            if (m.isToggled()) {
            	//Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getName()) - 6, yCount, sr.getScaledWidth(), yCount + 10, 0xFFFFFFFF);
            	Minecraft.fontRendererObj.drawString(m.getDisplayName(), sr.getScaledWidth() - Minecraft.fontRendererObj.getStringWidth(m.getDisplayName()) - 3, (int) (yCount + 1.3), RainbowUtils.effect(yCount*200000L,0.5f,1).getRGB() /*RainbowUtils.effect(1, 0.5F, 1).getRGB()*/);
                yCount += 10;
                index++;
            }
            
        }
           
    }

		}


