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

		//Render
			modules.add(new HUD());
			modules.add(new ClickGUI());
			modules.add(new Fullbright());
			modules.add(new PlayerESP());

		//Player
			modules.add(new NoFall());
			modules.add(new Regen());

		//Movement
			modules.add(new Velocity());
			modules.add(new Fly());
			modules.add(new Scaffold());
			modules.add(new Sprint());
			modules.add(new HighJump());
			modules.add(new LongJump());
			modules.add(new Bhop());
			modules.add(new Step());
			modules.add(new Safewalk());
			modules.add(new FlyHypixel());
			modules.add(new Speed());

		//Combat
			modules.add(new Antibot());
			modules.add(new Aura());

		//Misc
			modules.add(new Disabler());
			modules.add(new ChestStealer());

	}
	
    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
