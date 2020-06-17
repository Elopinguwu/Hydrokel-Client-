package me.hydrokel.client.Utils;

import me.hydrokel.client.Eventbus.Events.EventMove;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class MovementUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();

	public static float getDirection(EntityLivingBase e) {
	    float yaw = e.rotationYaw;
	    float forward = e.moveForward;
	    float strafe = e.moveStrafing;
	    yaw += (forward < 0.0F ? 180 : 0);
	    if (strafe < 0.0F) {
	    	yaw += (forward == 0.0F ? 90 : forward < 0.0F ? -45 : 45);
	    }
	    if (strafe > 0.0F) {
	    	yaw -= (forward == 0.0F ? 90 : forward < 0.0F ? -45 : 45);
	    }
	    return yaw * 0.017453292F;
	}
	public static void strafe(final float speed) {
		if (!isMoving())
			return;

		final double yaw = getDirection();
		mc.thePlayer.motionX = -Math.sin(yaw) * speed;
		mc.thePlayer.motionZ = Math.cos(yaw) * speed;
	}
	
	public static void setSpeed(Entity e, double speed){
		e.motionX = (-MathHelper.sin(getDirection()) * speed);
		e.motionZ = (MathHelper.cos(getDirection()) * speed);
	}
	
	public static double getSpeed(EntityLivingBase e){
	    return Math.sqrt(square(e.motionX) + square(e.motionZ));
	}
	
	public static float getDirection() { return MovementUtils.getDirection(mc.thePlayer); }
	public static void setSpeed(double speed){ MovementUtils.setSpeed((Entity)mc.thePlayer, speed); }
	public static double getSpeed() { return MovementUtils.getSpeed(mc.thePlayer); }
	
	public static double square(double in){
	    return in * in;
	}
	  
	public static boolean isMoving(){
		return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
	}
	
	public static float getSpeedMotion() {
        return (float)Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
     }
	
    public static void setMotion(EventMove em, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            em.setX(0.0D);
            em.setZ(0.0D);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }
    }
    
	public static void Damage(){
		for (int i = 0; i < 80.0 + 40.0 * (0.5 - 0.5); ++i) {
 			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.049, Minecraft.getMinecraft().thePlayer.posZ, false));
 			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
 		}
 		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
	}
    
	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.272;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + (0.2 * amplifier);
        }
        return baseSpeed;
    }
	
	public static void vClip(double d) {
	    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + d, mc.thePlayer.posZ);
	}
	
	public static void hClip(double offset) {
        mc.thePlayer.setPosition(mc.thePlayer.posX + -MathHelper.sin(getDirection()) * offset, mc.thePlayer.posY, mc.thePlayer.posZ + MathHelper.cos(getDirection()) * offset);
	}
	
	public static void HclipMotion(double offset) {
	  if(mc.thePlayer.ticksExisted % (1 * 1)== 0) {
		  MovementUtils.setSpeed(offset);
	  } else {
		  MovementUtils.setSpeed(0);
	  }
	}
	  
		public static void SelfDamage(int damage) {
			if (damage < 1)
				damage = 1;
			if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
				damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

			double offset1 = 0.3;
			if (mc.thePlayer != null && mc.thePlayer != null && mc.thePlayer.onGround) {
				for (int i = 0; i <= ((3 + damage) / offset1); i++) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY + offset1, mc.thePlayer.posZ, false));
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((1 + damage) / offset1))));
				}
			}
	  
	}
}