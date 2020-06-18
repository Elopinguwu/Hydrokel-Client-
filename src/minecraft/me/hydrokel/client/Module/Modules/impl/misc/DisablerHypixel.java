package me.hydrokel.client.Module.Modules.impl.misc;

import com.google.common.eventbus.Subscribe;
import javafx.print.Printer;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventAntiKB;
import me.hydrokel.client.Eventbus.Events.EventGetPacket;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Eventbus.Events.PacketEvent;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.input.Keyboard;

public class DisablerHypixel extends Module {


    public DisablerHypixel() { super("DisablerHypixel", Keyboard.KEY_NONE, Category.MISC); }
    private boolean laggedback, felldown;
    private float[] lastrot = new float[2];

    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir

    //J'ai skid mais je sais pas comment il marche faudrais voir
    //J'ai skid mais je sais pas comment il marche faudrais voir

    //J'ai skid mais je sais pas comment il marche faudrais voir


    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (felldown) {
            if (event.isPre()) {
                if (mc.thePlayer.movementInput.jump) {
                    mc.thePlayer.motionY = 3.0;
                } else if (mc.thePlayer.movementInput.sneak) {
                    mc.thePlayer.motionY = -3.0;
                } else {
                    mc.thePlayer.motionY = 0.0;
                }
                if (laggedback) {
                    setMoveSpeed(0.5);
                } else {
                    mc.thePlayer.movementInput.moveForward = 0;
                    mc.thePlayer.movementInput.moveStrafe = 0;
                    event.setYaw(lastrot[0]);
                    event.setPitch(lastrot[1]);
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01, mc.thePlayer.posZ);
                    } else {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ);
                    }
                }
            }
        } else if (mc.thePlayer.fallDistance > 0.0) {
            felldown = true;
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (!event.isSending() && felldown && !mc.isSingleplayer() && mc.theWorld != null) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
                if (mc.thePlayer.posY > packet.getY()) {
                    //Printer.print("Gerald Figley started a call. Today at 9:11 AM");
                    laggedback = true;
                }
            }
        }
    }


    @Override
    public void onEnable() {
        if (mc.thePlayer != null) {
            laggedback = false;
            felldown = false;
            lastrot[0] = mc.thePlayer.rotationYaw;
            lastrot[1] = mc.thePlayer.rotationPitch;
            if (mc.thePlayer.onGround) mc.thePlayer.jump();
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    }

    private void setMoveSpeed(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            mc.thePlayer.motionX = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
            mc.thePlayer.motionZ = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
        }
    }
}