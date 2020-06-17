package me.hydrokel.client.Module.Modules.impl.movement;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

public class HighJump extends Module {

    public HighJump() {
        super("HighJump", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    private boolean state = false;
    private boolean meh = false;



    @Override
    public void setup(){

        Main.instance.setmgr.rSetting(new Setting("Height", this, 2.5, 0.5, 5, false));
    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "HighJump", "You untoggled HighJump.", 1));
        super.onDisable();
        mc.thePlayer.motionY = 0;
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        state = false;
        meh = false;
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "HighJump", "You toggled HighJump.", 1));
        state = false;
        super.onEnable();
        meh = false;

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

        if(mc.thePlayer.hurtTime > 0 && !state){
            mc.thePlayer.motionY = Main.instance.setmgr.getSettingByName("Height").getValDouble();
            state=true;

        }

    }

    @EventTarget
    public void onGay(EventMotion oof) {
        if (mc.thePlayer.onGround && !meh) {
            mc.thePlayer.jump();
            mc.timer.timerSpeed = 1;
            meh = true;
            oof.setY(mc.thePlayer.posY + 3);
        }

    }

}
