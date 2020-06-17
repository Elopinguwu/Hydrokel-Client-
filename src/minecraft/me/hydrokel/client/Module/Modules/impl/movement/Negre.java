package me.hydrokel.client.Module.Modules.impl.movement;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventBoudingBox;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventSendPacket;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

import java.util.ArrayList;
import java.util.Random;

public class Negre extends Module {
    public Negre() {
        super("Negre", Keyboard.KEY_NONE, Category.MISC);

    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        Packet packet = event.getPacket();
        if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
            C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction) event.getPacket();
            mc.thePlayer.sendQueue.addToSendQueue((Packet) new C0FPacketConfirmTransaction(2147483647, packetConfirmTransaction.getUid(), false));
            event.setCancelled(true);
        }
        if (event.getPacket() instanceof C00PacketKeepAlive) {
            mc.thePlayer.sendQueue.addToSendQueue((Packet) new C00PacketKeepAlive(Integer.MIN_VALUE + (new Random()).nextInt(100)));
            event.setCancelled(true);
        }
    }
}


