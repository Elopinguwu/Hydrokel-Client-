package me.hydrokel.client.Module.Modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.*;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Utils.MovementUtils;

public class HypixelFly extends Module {
    private int level = 1;
    private TimerUtil timer = new TimerUtil();
    private boolean decreasing2, hypixelboost, canboost, nigga;
    private double lastDist, moveSpeed, starty;
    private BooleanValue viewbob = new BooleanValue("ViewBob", true);
    private NumberValue<Double> flyspeed = new NumberValue("FlySpeed", 2.0, 0.8, 3.5, 0.1);
    private float timervalue;

    public HypixelFly() {
        super("HypixelFly", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        MovementUtils.setSpeed(0);
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        mc.thePlayer.motionY = 0;
        nigga = false;

        NotificationManager.show(new Notification(NotificationType.INFO, "InventoryMove", "You untoggled InventoryMove.", 1));
        super.onDisable();
    }

      @Override
      public void onEnable() {
          NotificationManager.show(new Notification(NotificationType.INFO, "InventoryMove", "You toggled InventoryMove.", 1));
          super.onEnable();
          if (mc.theWorld != null && mc.thePlayer != null) {
              nigga = false;
              level = 1;
              moveSpeed = 0.1D;
              lastDist = 0.0D;

              canboost = true;
              {
                  double motionY = 0.40123128;
                  timervalue = 1.0F;
                  if (mc.thePlayer.onGround) {
                      if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.isCollidedVertically) {
                          if (mc.thePlayer.isPotionActive(Potion.jump))
                              motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                          mc.thePlayer.motionY = motionY;
                      }
                      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.28E-10D, mc.thePlayer.posZ);
                      level = 1;
                      moveSpeed = 0.1D;
                      hypixelboost = true;
                      lastDist = 0.0D;
                  } else {
                      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.26E-10D, mc.thePlayer.posZ);
                  }
                  timer.reset();
              }
          }
      }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (mc.thePlayer.fallDistance > 0 && !nigga) {
            nigga = true;
        }
        if (nigga) {
            lastDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
            if (canboost && hypixelboost) {
                timervalue += decreasing2 ? -0.01 : 0.05;
                if (timervalue >= 1.4) {
                    decreasing2 = true;
                }
                if (timervalue <= 0.9) {
                    decreasing2 = false;
                }
                if (timer.reach(2000)) {
                    canboost = false;
                }
            }
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 0.4, mc.thePlayer.posZ);
                mc.thePlayer.motionY = 0.8;
                mc.thePlayer.motionX *= 0.1;
                mc.thePlayer.motionZ *= 0.1;
            }
            if ((mc.thePlayer.ticksExisted % 2) == 0) {
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + MathUtils.getRandomInRange(0.00000000000001235423532523523532521, 0.0000000000000123542353252352353252 * 10), mc.thePlayer.posZ);
            }
            mc.thePlayer.motionY = 0;
        }
    }


    @Subscribe
    public void onMotion(EventMotion event) {
        float yaw = mc.thePlayer.rotationYaw;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        double forward = mc.thePlayer.movementInput.moveForward;
        double mx = -Math.sin(Math.toRadians(yaw)), mz = Math.cos(Math.toRadians(yaw));

        if (forward == 0.0F && strafe == 0.0F) {
            event.setX(0);
            event.setZ(0);
        }
        if (forward != 0 && strafe != 0) {
            forward = forward * Math.sin(Math.PI / 4);
            strafe = strafe * Math.cos(Math.PI / 4);
        }
        double motionY = 0.40123128;
        if (level != 1) {
            if (level == 2) {
                if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround && !nigga) {
                    mc.thePlayer.damagePlayer();
                    level = 3;
                    if (mc.thePlayer.isPotionActive(Potion.jump))
                        motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                    event.setY(mc.thePlayer.motionY = motionY);
                    moveSpeed *= 2.149;
                    nigga = true;
                }
            } else if (level == 3) {
                level = 4;
                double difference = 0.23D * (lastDist - MovementUtils.getBaseMoveSpeed());
                moveSpeed = lastDist - difference;
            } else {
                if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
                    level = 1;
                }
                moveSpeed = lastDist - lastDist / 159.0D;
            }
        } else {
            level = 2;
            double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.526 : 2.034;
            moveSpeed = boost * MovementUtils.getBaseMoveSpeed() - 0.01D;
        }
        moveSpeed = Math.max(moveSpeed, MovementUtils.getBaseMoveSpeed());
        event.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        event.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
        if (forward == 0.0F && strafe == 0.0F) {
            event.setX(0.0);
            event.setZ(0.0);
        }
    }
}

