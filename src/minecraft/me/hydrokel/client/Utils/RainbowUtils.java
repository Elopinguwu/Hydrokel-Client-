package me.hydrokel.client.Utils;

import java.awt.Color;

public class RainbowUtils {

	public static Color effect(long d, float brightness, float speed) {
		float hue = (float) (System.nanoTime() + (d * speed)) / 1.0E09F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, 1F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed()/255.0F, c.getGreen()/255.0F, c.getBlue()/255.0F, c.getAlpha()/255.0F);
	}
	
	public static Color rainbowEffectLong(long offset, float fade) {
		float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F*fade, c.getGreen() / 255.0F*fade, c.getBlue() / 255.0F*fade, c.getAlpha() / 255.0F);
	}
}
