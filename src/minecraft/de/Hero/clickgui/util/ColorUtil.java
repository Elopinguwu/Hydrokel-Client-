package de.Hero.clickgui.util;

import java.awt.Color;

import me.hydrokel.client.Main;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Main.instance.setmgr.getSettingByName("GuiRed").getValDouble(), (int)Main.instance.setmgr.getSettingByName("GuiGreen").getValDouble(), (int)Main.instance.setmgr.getSettingByName("GuiBlue").getValDouble());
	}
}
