package me.hydrokel.client;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import me.hydrokel.client.Eventbus.EventManager;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventKey;
import me.hydrokel.client.Module.ModuleManager;

public class Main {
	public String name = "ClientBase", verssion = "0.1", creator = "Eloping";
	
    public static Main instance = new Main();
    
    public SettingsManager setmgr;
    public EventManager eventManager = new EventManager();
    public ModuleManager moduleManager;
    public ClickGUI clickGui;
	
	public void startClient() {
		System.out.println("--------Client Load !!---------");
		
		setmgr = new SettingsManager();
		eventManager.register(this);
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		clickGui = new ClickGUI();
	}
	
	public void stopClient() {
		eventManager.unregister(this);
	}
	
    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
}
