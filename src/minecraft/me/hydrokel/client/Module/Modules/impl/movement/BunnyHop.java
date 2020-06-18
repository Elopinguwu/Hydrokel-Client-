package me.hydrokel.client.Module.Modules.impl.movement;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class BunnyHop extends Module {

    public BunnyHop() {
        super("BunnyHop", Keyboard.KEY_B, Category.MOVEMENT);

    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Cubecraft");
        options.add("Vanilla");

        Main.instance.setmgr.rSetting(new Setting("Bhop Mode", this, "Vanilla", options));
    }

    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "BunnyHop", "You untoggled BunnyHop.", 1));
        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "BunnyHop", "You toggled BunnyHop.", 1));
        super.onEnable();

    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if (Main.instance.setmgr.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("Cubecraft")) {


            MovementUtils.setSpeed(0.15);
            mc.timer.timerSpeed = 1.03F;
            if (mc.thePlayer.onGround) {
                mc.timer.timerSpeed = 1.1F;
                if (MovementUtils.isMoving())
                    MovementUtils.setSpeed(2.65);
                //mc.thePlayer.jump();
                mc.thePlayer.motionY += 0.24;

            } else {
                mc.timer.timerSpeed = 1F;
                MovementUtils.setSpeed(0.24);
            }
        }
    }

    {
        if (Main.instance.setmgr.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("Vanilla"))

            if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0.4;
                MovementUtils.setSpeed(0.8);
                mc.thePlayer.speedInAir = 0.027F;
            }
    }
}


