package me.hydrokel.client.Module.Modules.impl.movement;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.Utils.PlayerUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Bhop extends Module {

    public Bhop() {
        super("Bhop", Keyboard.KEY_B, Category.MOVEMENT);

    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Cubecraft");

        Main.instance.setmgr.rSetting(new Setting("Bhop Mode", this, "Cubecraft", options));
    }

    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Bhop", "You untoggled Bhop.", 1));
        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Bhop", "You toggled Bhop.", 1));
        super.onEnable();

    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if (Main.instance.setmgr.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("Cubecraft")) {
            if (MovementUtils.isMoving())
            MovementUtils.setSpeed(0.15);
            mc.timer.timerSpeed = 1.03F;
            if (mc.thePlayer.onGround) {
                if (MovementUtils.isMoving())
                    mc.timer.timerSpeed = 1.1F;
                if (MovementUtils.isMoving())
                    //MovementUtils.setSpeed(2.65);
                    MovementUtils.setSpeed(3);

                  //  mc.thePlayer.jump();
                if (MovementUtils.isMoving())
                   mc.thePlayer.motionY += 0.1;

            } else {
                mc.timer.timerSpeed = 1F;

                MovementUtils.setSpeed(0.24);
            }
        }
    }

    {


    }
}