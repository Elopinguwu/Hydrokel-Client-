package me.hydrokel.client.Module.Modules.impl.misc;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChestStealer extends Module {
    public int cursor = 0;
    public long current_miliseconds = 0;
    private boolean isLargeChest;
    private String inventoryName = "";
    private List<Slot> validSlots = new ArrayList<Slot>();
    private boolean isContainerOpen = false;


    public ChestStealer() {
        super("ChestStealer", 0, Category.MISC);
    }
    @Override
    public void setup(){
        Main.instance.setmgr.rSetting(new Setting("Pickup Delay", this, 70, 1, 500, false));

    }
    @Override
    public void onEnable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "ChestStealer", "You toggled ChestStealer.", 1));
        this.isContainerOpen = false;
        current_miliseconds = System.currentTimeMillis();

        super.onEnable();
    }
    @Override
    public void onDisable() {
        NotificationManager.show(new Notification(NotificationType.INFO, "ChestStealer", "You untoggled ChestStealer.", 1));
        current_miliseconds = System.currentTimeMillis();

        super.onDisable();
    }
    @EventTarget
    public void onTick(EventUpdate event) {
        if (mc.currentScreen != null && mc.currentScreen instanceof GuiChest) {

            GuiChest chest = (GuiChest) mc.currentScreen;
            if (!this.isContainerOpen) {
                validSlots = new ArrayList<Slot>();
                boolean isLargeChest = chest.inventorySlots.inventorySlots.size() == 90 ? true : false;
                int slotChestSize = isLargeChest ? 54 : 27;
                for (Slot s : chest.inventorySlots.inventorySlots) {
                    if (s != null && s.getHasStack() && s.inventory.getSizeInventory() == slotChestSize) {
                        inventoryName = s.inventory.getName();
                        validSlots.add(s);
                    }
                }
                Collections.shuffle(validSlots);
                cursor = 0;
                this.isContainerOpen = true;
            }

            if (validSlots.size() == 0) {
                return;
            }

            if (System.currentTimeMillis()-current_miliseconds >= (int) Main.instance.setmgr.getSettingByName("Pickup Delay").getValDouble()) {
                mc.playerController.windowClick(chest.inventorySlots.windowId, validSlots.get(cursor).slotNumber, 0, 1, mc.thePlayer);
                chest.updateScreen();
                current_miliseconds = System.currentTimeMillis();
                if (cursor >= validSlots.size()-1) {
                    cursor = 0;
                } else {
                    cursor++;
                }
            }




        } else {
            this.isContainerOpen = false;
        }
        super.onRender();
    }
}
