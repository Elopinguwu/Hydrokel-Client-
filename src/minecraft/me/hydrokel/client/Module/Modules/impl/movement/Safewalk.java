package me.hydrokel.client.Module.Modules.impl.movement;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventSafeWalk;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

public class Safewalk extends Module {
    public Safewalk() {
        super("Safewalk",0, Category.MOVEMENT);
    }
    @EventTarget
    public void onSafeWalk(EventSafeWalk event) {
        System.out.println("true");
        event.setCancelled(true);
    }
}
