package me.hydrokel.client.Module.Modules.impl.movement;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);	
	}
	
	@Override
	public void onDisable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "Sprint", "You untoggled Sprint.", 1));
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "Sprint", "You toggled Sprint.", 1));
		super.onEnable();

	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.moveForward > 0) {
			mc.thePlayer.setSprinting(true);
		}
	}
}
