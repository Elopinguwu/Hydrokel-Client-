package me.hydrokel.client.Module.Modules.impl.movement;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;


public class SpeedHack extends Module {

        public SpeedHack() {
            super("SpeedHack", Keyboard.KEY_NONE, Category.MOVEMENT);
        }

        @Override
        public void onDisable() {
            NotificationManager.show(new Notification(NotificationType.INFO, "SpeedHack", "You untoggled SpeedHack.", 1));
            super.onDisable();
        }

        @Override
        public void onEnable() {
            NotificationManager.show(new Notification(NotificationType.INFO, "SpeedHack", "You toggled SpeedHack.", 1));
            super.onEnable();

        }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Cubecraft");
        Main.instance.setmgr.rSetting(new Setting("SpeedHack Mode", this, "Cubecraft", options));


    }

        @EventTarget
        public void onUpdate(EventUpdate event) {
            if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Cubecraft")) {
                mc.timer.timerSpeed = 0.31F;
                if (MovementUtils.isMoving()) {
                    MovementUtils.setSpeed(0.40);
                    if (mc.thePlayer.ticksExisted % 3 == 0) {
                        //mc.thePlayer.motionY = 0.25;
                        MovementUtils.setSpeed(3.20);
                    }
                }

            }
        }
    }