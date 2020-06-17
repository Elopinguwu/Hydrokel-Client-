package me.hydrokel.client.Module.Modules.impl;

import java.util.ArrayList;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Module.Category;
import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Module;

public class BaseModule extends Module {

	public BaseModule() {
		super("BaseModule", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
    @Override
    public void setup(){
    	ArrayList<String> options = new ArrayList<String>();
    	options.add("1MODE");
    	options.add("2MODE");

    	Main.instance.setmgr.rSetting(new Setting("BASE_MODE", this, "1MODE", options));
    	Main.instance.setmgr.rSetting(new Setting("Slider", this, 10, 1, 20, true));
    	Main.instance.setmgr.rSetting(new Setting("Boolean", this, false));
    }

    @Override
    public void onEnable() {
    		
    	super.onEnable();
    }
    
    @Override
    public void onDisable() {
    	   	
    	super.onDisable();
    }
    
    @EventTarget
    public void onUpdate(EventUpdate e) {

    	if(Main.instance.setmgr.getSettingByName("BASE_MODE").getValString().equalsIgnoreCase("1MODE")){
    		
    		mc.timer.timerSpeed = (float) Main.instance.setmgr.getSettingByName("Slider").getValDouble();
    	} 
    	
    	if(Main.instance.setmgr.getSettingByName("BASE_MODE").getValString().equalsIgnoreCase("2MODE")){
    		
    	}  
    	
    	
    }
}
