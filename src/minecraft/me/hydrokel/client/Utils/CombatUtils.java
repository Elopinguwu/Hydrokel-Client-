package me.hydrokel.client.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class CombatUtils {
	
	//InfiniteReach
	static double x;
	static double y;
	static double z;
	static double xPreEn;
	static double yPreEn;
	static double zPreEn;
	static double xPre;
	static double yPre;
	static double zPre;
	
	public static Minecraft mc = Minecraft.getMinecraft();
	private static List<EntityLivingBase> entity = new ArrayList();
        
    public static boolean isInReach(EntityLivingBase entity, double reach) {
    	return mc.thePlayer.getDistanceToEntity(entity) <= reach;
    	
    	//InfiniteReach
    	
    }
    
    public static boolean isValid(EntityLivingBase ent) {
    	if(ent == null)
    		return false;
    	
    	if(ent == mc.thePlayer)
    		return false;
    	
    	/*if(!ent.isEntityAlive())// A RETIRé pour bypass Erisium enfin presque
    		return false;*/
    	
    	if(ent instanceof EntityArmorStand)
    		return false;
    	
    	if(ent instanceof EntityVillager)
    		return false;

    	//if(ent.ticksExisted < 100)
    	   // return false;
    	
    	if(ent.isInvisible())
    		return true;
    	
    	if(ent instanceof EntitySquid)
    		return false;
    	
    	/*if(lmao.instance.moduleManager.getModuleByName("Teams").isToggled()) {
    	if(mc.thePlayer.isOnSameTeam(ent))
    		return false;
    	}*/
    	
    	//if(ent instanceof EntityPlayer) {
    	// EntityPlayer ep = (EntityPlayer)ent;
    	// if(Main.instance.friends.isFriend(ep.getName()))
    	//	 return false;
    	//}
    	
    	return true;
    }
    
    public static void attack(EntityLivingBase entity) {	
    	mc.thePlayer.setSprinting(true);
		mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, Action.ATTACK)); //KEEPSPRINT
		//mc.playerController.attackEntity(mc.thePlayer, entity); // NO KEEPSPRINT 
        mc.thePlayer.swingItem();
        mc.thePlayer.onEnchantmentCritical(entity);
    }
           
    public static float[] getEntityRotations(Entity player, EntityLivingBase target)
	  {
	    double posX = target.posX - player.posX;
	    double posY = target.posY + target.getEyeHeight() - (player.posY + player.getEyeHeight() + 0.5);
	    double posZ = target.posZ - player.posZ;
	    double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
	    float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(posY, var14) * 180.0D / 3.141592653589793D);
	    return new float[] { yaw + getRandomRotations()[0], pitch + getRandomRotations()[1] };
	  }
	  
	  private static Random getRandom()
	  {
	    return new Random();
	  }
	  
	  private static float[] getRandomRotations()
	  {
	    float dif = getRandom().nextFloat() * 0.1F;
	    boolean pos = getRandom().nextBoolean();
	    float yaw;
	    if (pos) {
	      yaw = dif;
	    } else {
	      yaw = -dif;
	    }
	    float dif1 = getRandom().nextFloat() * 0.1F;
	    boolean pos1 = getRandom().nextBoolean();
	    float pitch;
	    if (pos1) {
	      pitch = dif1;
	    } else {
	      pitch = -dif1;
	    }
	    return new float[] { yaw, pitch };
	  }
        
	public static EntityLivingBase findEntity() {
        double Dist = Double.MAX_VALUE;
        EntityLivingBase entity = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            if ((object instanceof EntityLivingBase)) {
                EntityLivingBase e = (EntityLivingBase) object;
                if (CombatUtils.isValid(e) && (mc.thePlayer.getDistanceSqToEntity(e) < Dist)) {
                    entity = e;
                    Dist = mc.thePlayer.getDistanceToEntity(entity);
                }
            }
        }
        return entity;
    }
	
	public static EntityLivingBase findEntity2() {
        double Dist = Double.MAX_VALUE;
        EntityLivingBase entity = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            if ((object instanceof EntityLivingBase)) {
                EntityLivingBase e = (EntityLivingBase) object;
            }
        }
        return entity;
    }
	
    public static float getPitch(EntityLivingBase ent) {
        double y = (ent.posY + ent.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        y /= mc.thePlayer.getDistanceToEntity(ent);
        double pitch = Math.asin(y) * 57;
        pitch = -pitch;
        return (float)pitch;
    }
    
    public static float getYaw(EntityLivingBase ent) {
        double x = ent.posX - mc.thePlayer.posX;
        double z = ent.posZ - mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57;
        yaw = -yaw;
        return (float)yaw;
    }
    
    public static boolean rayTrace(EntityLivingBase e) {
    	Vec3 entityIn = new Vec3(e.posX, e.posY + (double)e.getEyeHeight(), e.posZ);
    	Vec3 player = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
		boolean raytrace = mc.theWorld.rayTraceBlocks(entityIn, player, false) == null;
		return raytrace;
	}
    
	public static EntityLivingBase findEntity(double distanceMax) {
        double Dist = distanceMax;
        EntityLivingBase entity = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            if ((object instanceof EntityLivingBase)) {
                EntityLivingBase e = (EntityLivingBase) object;
                if (CombatUtils.isValid(e) && (mc.thePlayer.getDistanceToEntity(e) < Dist) && CombatUtils.isValid(e)) {
                    entity = e;
                    Dist = mc.thePlayer.getDistanceToEntity(entity);
                }
            }
        }
        return entity;
    }
	
    public static EntityLivingBase getBestEntityByHealt() {
    	entity.clear();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) e;
                if (isValid(ent)) {
                	entity.add(ent);
                }
            }
        }
        if (entity.isEmpty()) {
            return null;
        }
        sortTargetsByHealt();
        return entity.get(0);
    }
    
    public static EntityLivingBase getBestEntityByDistance() {
    	entity.clear();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) e;
                if (isValid(ent)) {
                	entity.add(ent);
                }
            }
        }
        if (entity.isEmpty()) {
            return null;
        }
        sortTargetsByDistance();
        return entity.get(0);
    }
    
    public static EntityLivingBase getBestEntityByCycle(float yaw) {
    	entity.clear();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) e;
                if (isValid(ent)) {
                	entity.add(ent);
                }
            }
        }
        if (entity.isEmpty()) {
            return null;
        }
        sortTargetsByCycle(yaw);;
        return entity.get(0);
    }
    
    private static void sortTargetsByHealt() {
    	entity.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
    }
    
    private static void sortTargetsByDistance() {
        entity.sort(Comparator.comparingDouble(player -> mc.thePlayer.getDistanceToEntity(player)));
    }
    
    private static void sortTargetsByCycle(float yaw) {
        entity.sort(Comparator.comparingDouble(player -> yawDistCycle(player, yaw)));
    }
    
    private double yawDist(EntityLivingBase e) {
        final Vec3 difference = e.getPositionVector().addVector(0.0, e.getEyeHeight() / 2.0f, 0.0).subtract(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0));
        final double d = Math.abs(mc.thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f)) % 360.0f;
        return (d > 180.0f) ? (360.0f - d) : d;
    }
    
    private static double yawDistCycle(EntityLivingBase e, float yaw) {
        final Vec3 difference = e.getPositionVector().addVector(0.0, e.getEyeHeight() / 2.0f, 0.0).subtract(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0));
        final double d = Math.abs(yaw - Math.atan2(difference.zCoord, difference.xCoord)) % 90.0f;
        return d;
    }
	

}
