package me.hydrokel.client.Module.Modules.impl.combat;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventReceivePacket;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.lwjgl.input.Keyboard;
import net.minecraft.network.play.server.S14PacketEntity;

import java.util.ArrayList;
import java.util.List;


public class Antibot extends Module {

    public Antibot() {
        super("Antibot", Keyboard.KEY_NONE, Category.COMBAT);
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Fly", "You toggled Antibot.", 1));

        super.onEnable();

    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Disabler", "You untoggled Antibot.", 1));
        super.onDisable();

    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Invisible");

        Main.instance.setmgr.rSetting(new Setting("Antibot Mode", this, "Invisible", options));
    }


    @EventTarget
    public void onUpdate(EventUpdate event) {

        if(Main.instance.setmgr.getSettingByName("Antibot Mode").getValString().equalsIgnoreCase("Invisible"))
        for (Object entity : mc.theWorld.loadedEntityList)
                if (((Entity) entity).isInvisible() && entity != mc.thePlayer)
                    mc.theWorld.removeEntity((Entity) entity);
    }}




