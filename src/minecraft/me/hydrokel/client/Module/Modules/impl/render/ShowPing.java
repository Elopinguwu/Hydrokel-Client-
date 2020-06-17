package me.hydrokel.client.Module.Modules.impl.render;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Module.ModuleManager;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class ShowPing extends Module {
    public int Ping = 0;
    public ShowPing() {
        super("ShowPing", Keyboard.KEY_J, Category.RENDER);
    }

    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "ShowPing", "You untoggled ShowPing.", 1));
        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "ShowPing", "You toggled ShowPing.", 1));
        super.onEnable();

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
      Ping =  mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
        mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(Ping)));

    }
}