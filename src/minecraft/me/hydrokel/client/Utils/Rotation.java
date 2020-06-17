package me.hydrokel.client.Utils;

import net.minecraft.entity.player.EntityPlayer;

public class Rotation {
	
	float yaw;
	float pitch;
	
	public void toPlayer(EntityPlayer player) {
        player.rotationYaw = yaw;
        player.rotationPitch = pitch;
	}
	
}
