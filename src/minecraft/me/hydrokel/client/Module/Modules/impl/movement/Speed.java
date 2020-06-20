package me.hydrokel.client.Module.Modules.impl.movement;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Eventbus.Events.MotionUpdateEvent;
import me.hydrokel.client.Eventbus.Events.MoveEvent;
import me.hydrokel.client.Eventbus.Events.UpdateActionEvent;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.Utils.PlayerUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {

    /////////////////////////
    private double moveSpeed;
    private double lastDist;
    private double y;
    private int stage_;
    private int hops_;
    /////////////////////////

    public Speed() {
        super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.625F;
        mc.timer.timerSpeed = 1.0F;
        NotificationManager.show(new Notification(NotificationType.INFO, "Speed", "You untoggled Speed.", 1));
        super.onDisable();
    }

    @Override
    public void onEnable() {

        this.y = 0.0D;
        this.hops_ = 1;
        this.moveSpeed = MovementUtils.getBaseMoveSpeed();
        this.lastDist = 0.0D;
        this.stage_ = 0;
        NotificationManager.show(new Notification(NotificationType.INFO, "Speed", "You toggled Speed.", 1));
        super.onEnable();


    }

    @EventTarget
    public void onActionUpdate(UpdateActionEvent event) {

        if (event.isSneakState()) {
            event.setSneakState(false);

        }
    }

    @EventTarget
    public final void onMove(MoveEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        if (PlayerUtils.isInLiquid()) {
            return;
        }

        if (player.isMoving()) {

            double difference, rounded, d1;


            if (player.onGround && player.isCollidedVertically) {
                mc.timer.timerSpeed = 1.0F;
                event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.41999998688697815D);
                this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.5D;

                difference = 0.6200000047683716D * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist + difference;

                if (mc.theWorld.getCollidingBoundingBoxes((Entity) player, player.getEntityBoundingBox().offset(0.0D, player.motionY, 0.0D)).size() > 0 || (player.isCollidedVertically && player.onGround)) {
                    this.stage_ = 1;
                    mc.timer.timerSpeed = 1.085F;
                    this.hops_++;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 149.0D;

            }
            this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());

        }
        MovementUtils.setSpeed(this.moveSpeed);
        this.stage_++;
    }


        @EventTarget
        public final void onMotionUpdate(MotionUpdateEvent event) {
            if (event.isPre()) {
                if (PlayerUtils.isInLiquid()) {
                    return;
                }

                EntityPlayerSP player = mc.thePlayer;

                if (player.isMoving()) {
                    if (player.isCollidedVertically) {
                        event.setPosY(mc.thePlayer.posY + 7.435E-4D);
                    }
                }
                double xDist = player.posX - player.prevPosX;
                double zDist = player.posZ - player.prevPosZ;
                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
            }
        }
}










