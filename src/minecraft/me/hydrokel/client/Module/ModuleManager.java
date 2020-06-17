package me.hydrokel.client.Module;

import java.util.ArrayList;

import me.hydrokel.client.Module.Modules.impl.misc.*;
import me.hydrokel.client.Module.Modules.impl.combat.*;
import me.hydrokel.client.Module.Modules.impl.movement.*;
import me.hydrokel.client.Module.Modules.impl.player.NoFall;
import me.hydrokel.client.Module.Modules.impl.player.Regen;
import me.hydrokel.client.Module.Modules.impl.render.*;
import me.hydrokel.client.Module.Modules.impl.render.HUD;
//import me.hydrokel.client.Module.Modules.impl.player.*;

public class  ModuleManager {
	
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManager() {
		//Module-register//
		modules.add(new HUD());
		modules.add(new Velocity());
		modules.add(new Fly());
		modules.add(new Sprint());
		modules.add(new ClickGUI());
		modules.add(new HighJump());
		modules.add(new NoFall ());
		modules.add(new LongJump());
		modules.add(new Fullbright());
		modules.add(new BunnyHop());
		modules.add(new PlayerESP());
		modules.add(new Antibot());
		modules.add(new Aura());
		modules.add(new Step());
		modules.add(new Disabler());
		modules.add(new ShowPing());
		modules.add(new Regen());
		modules.add(new Negre());
		modules.add(new SpeedHack());
		//modules.add(new BaseModule());
	}
	
    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
