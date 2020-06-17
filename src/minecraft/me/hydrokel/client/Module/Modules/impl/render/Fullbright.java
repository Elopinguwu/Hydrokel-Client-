package me.hydrokel.client.Module.Modules.impl.render;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

public class Fullbright extends Module {

    private float old;

    public Fullbright() {

        super("Fullbright", Keyboard.KEY_J, Category.RENDER);
    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Fullbright", "You untoggled Fullbright.", 1));
        mc.gameSettings.gammaSetting = old;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Fullbright", "You toggled Fullbright.", 1));
        old = mc.gameSettings.gammaSetting;
        super.onEnable();

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.gameSettings.gammaSetting = 1000000;
        }
    }
