package me.hydrokel.client.Utils;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationsUtils {
	public static Vec3 getEyesPos()
	{
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		
		return new Vec3(player.posX,
				player.posY + player.getEyeHeight(),
				player.posZ);
	}
	
	public static Vec3 getClientLookVec()
	{
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		float f = 0.017453292F;
		float pi = (float)Math.PI;
		
		float f1 = MathHelper.cos(-player.rotationYaw * f - pi);
		float f2 = MathHelper.sin(-player.rotationYaw * f - pi);
		float f3 = -MathHelper.cos(-player.rotationPitch * f);
		float f4 = MathHelper.sin(-player.rotationPitch * f);
		
		return new Vec3(f2 * f3, f4, f1 * f3);
	}
	
	public static Rotation getNeededRotations(Vec3 vec)
	{
		Vec3 eyesPos = getEyesPos();
		
		double diffX = vec.xCoord - eyesPos.xCoord;
		double diffY = vec.yCoord - eyesPos.yCoord;
		double diffZ = vec.zCoord - eyesPos.zCoord;
		
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		
		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
		
		return new Rotation(yaw,pitch);
	}
	
	
	

	public static final class Rotation
	{
		private final float yaw;
		private final float pitch;
		
		public Rotation(float yaw, float pitch)
		{
			this.yaw = yaw;
			this.pitch = pitch;
		}
		
		public float getYaw()
		{
			return yaw;
		}
		
		public float getPitch()
		{
			return pitch;
		}
	}
}
