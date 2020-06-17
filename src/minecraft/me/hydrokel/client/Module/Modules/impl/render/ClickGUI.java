package me.hydrokel.client.Module.Modules.impl.render;

import de.Hero.settings.Setting;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");
        Main.instance.setmgr.rSetting(new Setting("Design", this, "New", options));
        Main.instance.setmgr.rSetting(new Setting("Sound", this, false));
        Main.instance.setmgr.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
        Main.instance.setmgr.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
        Main.instance.setmgr.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.displayGuiScreen(Main.instance.clickGui);
        toggle();
    }
}
