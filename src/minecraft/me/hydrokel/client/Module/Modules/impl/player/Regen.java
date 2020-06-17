package me.hydrokel.client.Module.Modules.impl.player;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class Regen extends Module {

    public Regen() {
        super("Regen", Keyboard.KEY_NONE, Category.PLAYER);
    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Regen", "You untoggled Regen.", 1));
        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Regen", "You toggled Regen.", 1));
        super.onEnable();

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!mc.thePlayer.capabilities.isCreativeMode && mc.thePlayer.getFoodStats().getFoodLevel() > 17

                && mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth() && mc.thePlayer.getHealth() != 0) {
                     mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                   mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
              mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
              mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
              mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());




        }

        }

    }





