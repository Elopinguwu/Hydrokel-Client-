package me.hydrokel.client.Module.Modules.impl.combat;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventAntiKB;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", Keyboard.KEY_NONE, Category.COMBAT);	
	}
	
	@Override
	public void onDisable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "Velocity", "You untoggled Velocity.", 1));
		super.onDisable();
		
	}
	
	@Override
	public void onEnable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "Velocity", "You toggled Velocity.", 1));
		super.onEnable();

	}

	@EventTarget
	public void onVelocity(EventAntiKB event) {
		event.setCancelled(true);
	}
}
