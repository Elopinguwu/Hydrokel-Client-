package me.hydrokel.client.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtils {

    public static void forcePacket(Packet packet) {
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void queuePacket(Packet packet) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }

}
