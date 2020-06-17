package me.hydrokel.client.Module.Modules.impl.misc;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventAntiKB;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import org.lwjgl.input.Keyboard;

public class NameProtect extends Module {


    public NameProtect() { super("NameProtect", Keyboard.KEY_NONE, Category.MISC); }

        @Override
        public void onDisable() {
            super.onDisable();

        }

        @Override
        public void onEnable() {
            super.onEnable();

        }

        @EventTarget
        public void onUpdate(EventUpdate event) {

        }
    }