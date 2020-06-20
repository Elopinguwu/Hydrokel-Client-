package me.hydrokel.client.Module.Modules.impl.misc;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventSendPacket;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.TimeHelper;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

import java.util.ArrayList;

public class Disabler extends Module {

    TimeHelper timer;
    boolean shouldCancel, shouldCancel2;

    public Disabler() {
        super("Disabler", -1, Category.MISC);

        timer = new TimeHelper();


    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Erisium");
        options.add("C18PacketSpectate");
        Main.instance.setmgr.rSetting(new Setting("Disabler Mode", this, "Erisium", options));
    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Disabler", "You untoggled Disabler.", 1));
        super.onDisable();

        }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "Disabler", "You toggled Disabler.", 1));
        shouldCancel = shouldCancel2 = true;
    }


    @EventTarget
    public void onOwO(EventUpdate e) {
        //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(500, 500, 500, Integer.MAX_VALUE, Integer.MAX_VALUE, true)
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        Packet packet = event.getPacket();

        if (Main.instance.setmgr.getSettingByName("Disabler Mode").getValString().equalsIgnoreCase("Erisium")) {
            if (mc.theWorld != null && mc.thePlayer != null) {

                if (packet instanceof C0FPacketConfirmTransaction || packet instanceof C00PacketKeepAlive || packet instanceof S00PacketKeepAlive || packet instanceof S32PacketConfirmTransaction) {
                    event.setCancelled(true);
                }

                if (packet instanceof C00PacketKeepAlive && shouldCancel) {
                    event.setCancelled(true);

                    if (timer.hasReached(6000)) {
                        C00PacketKeepAlive keepAlivePacket = (C00PacketKeepAlive) packet;

                        shouldCancel = false;
                        mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive(keepAlivePacket.getKey()));
                        shouldCancel = true;
                        timer.reset();
                    }
                }
                if (packet instanceof C0FPacketConfirmTransaction && shouldCancel2) {
                    C0FPacketConfirmTransaction transactionPacket = (C0FPacketConfirmTransaction) packet;

                    event.setCancelled(true);
                    if (timer.hasReached(6000)) {
                        shouldCancel2 = false;
                        mc.thePlayer.sendQueue.addToSendQueue(new C0FPacketConfirmTransaction(transactionPacket.getWindowId(), transactionPacket.getUid(), transactionPacket.isAccepted()));
                        shouldCancel2 = true;
                        timer.reset();


                        if (Main.instance.setmgr.getSettingByName("Disabler Mode").getValString().equalsIgnoreCase("Rinaorc"))
                            mc.thePlayer.sendQueue.addToSendQueue((new C00PacketKeepAlive(-2147483647)));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(Float.MAX_VALUE, Float.MAX_VALUE, true));
                        {

                        }
                    }
                }

                if (Main.instance.setmgr.getSettingByName("Disabler Mode").getValString().equalsIgnoreCase("C18PacketSpectate"))
                    mc.thePlayer.sendQueue.addToSendQueue(new C18PacketSpectate());

            }

        }
    }
}
