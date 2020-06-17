package me.hydrokel.client.Module.Modules.impl.movement;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventMove;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;


public class Step extends Module {

    public Step() {
        super("Step", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<String>();
        options.add("Vanilla");
        options.add("Verus");
        options.add("Dev");

        Main.instance.setmgr.rSetting(new Setting("Step Mode", this, "Vanilla", options));
    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Step", "You untoggled Step.", 1));
        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Step", "You toggled Step.", 1));
        super.onEnable();

    }



    @EventTarget
    public void onMotion(EventMotion e) {
        if(Main.instance.setmgr.getSettingByName("Step Mode").getValString().equalsIgnoreCase("Verus")){
            if(mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.motionY = 0.42;
                MovementUtils.setSpeed(0.1);

            }

        }
    }


}