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
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

import java.util.ArrayList;

public class Negre extends Module {
    public Negre() {
        super("Negre", Keyboard.KEY_NONE, Category.MOVEMENT);

    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        Packet packet = event.getPacket();
              mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
              mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.18D, mc.thePlayer.posZ, true));
              mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.08D, mc.thePlayer.posZ, true));
             //je test des truc ici
        //test github 2
    }
}


