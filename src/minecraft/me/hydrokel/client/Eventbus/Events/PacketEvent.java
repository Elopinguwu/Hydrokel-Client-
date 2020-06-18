package me.hydrokel.client.Eventbus.Events;

import me.hydrokel.client.Eventbus.CancelableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancelableEvent {
    private boolean sending;
    private Packet packet;
    public PacketEvent(Packet packet, boolean sending) {
        this.packet = packet;
        this.sending = sending;
    }

    public Packet getPacket() {
        return packet;
    }

    public boolean isSending() {return sending; }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }}
