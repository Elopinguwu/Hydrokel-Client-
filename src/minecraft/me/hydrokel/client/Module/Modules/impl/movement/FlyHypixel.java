package me.hydrokel.client.Module.Modules.impl.movement;

import java.util.ArrayDeque;
import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.*;
import me.hydrokel.client.Main;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.*;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.util.Timer;
import org.lwjgl.input.Keyboard;






public class FlyHypixel extends Module {

        public FlyHypixel() {
            super("FlyHypixel", Keyboard.KEY_NONE, Category.MOVEMENT);
        }

    public final EnumOption<Mode> mode = new EnumOption("Mode", Mode.WATCHDOG);
    public final BoolOption viewBobbing = new BoolOption("View Bobbing", Boolean.valueOf(true));
    public final BoolOption multiplier = new BoolOption("Multiplier", Boolean.valueOf(false), () -> Boolean.valueOf((this.mode.getValue() == Mode.WATCHDOG)));
    public final DoubleOption multiplyspeed = new DoubleOption("Multiply Speed", 1.8D, () -> Boolean.valueOf((this.mode.getValue() == Mode.WATCHDOG)), 0.1D, 3.0D, 0.05D);
    public final DoubleOption multiplytime = new DoubleOption("Multiply Time", 1190.0D, () -> Boolean.valueOf((this.mode.getValue() == Mode.WATCHDOG)), 100.0D, 3000.0D, 50.0D);
    public final DoubleOption speed = new DoubleOption("Watchdog Speed", 1.0D, () -> Boolean.valueOf((this.mode.getValue() == Mode.WATCHDOG)), 0.5D, 1.0D, 0.05D);
    public final DoubleOption vanillaSpeed = new DoubleOption("Vanilla Speed", 5.0D, () -> Boolean.valueOf((this.mode.getValue() == Mode.VANILLA)), 0.1D, 7.0D, 0.1D);
    private final ArrayDeque<Packet<?>> arrayDeque = new ArrayDeque<>();

    private final Stopwatch stopwatch = new Stopwatch();

    private int ticks;
    private int stage;
    private CustomVec3 lastPos;
    private boolean tp;
    private double lastDist;
    private double moveSpeed;
    private double y;

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Hypixel");
        options.add("Mineplex");
        options.add("Disabler");

        Main.instance.setmgr.rSetting(new Setting("Fly Mode", this, "Hypixel", options));

    }

    @EventTarget
    public final void onSendPacket(SendPacketEvent event) {
        if (this.mode.getValue() == Mode.WATCHDOG &&
                this.stage == 0) {
            event.setCancelled();
        }
        if (this.mode.getValue() == Mode.MINEPLEX &&
                !this.stopwatch.elapsed(6100L)) {
            event.setCancelled();
        }

        if (this.mode.getValue() == Mode.DISABLER &&
                event.getPacket() instanceof C03PacketPlayer &&
                !this.tp) {
            event.setCancelled();
        }
    }



    @EventTarget
    public final void onReceivePacket(ReceivePacketEvent event) {
        if (this.mode.getValue() == Mode.MINEPLEX) {
            if ((event.getPacket() instanceof net.minecraft.network.play.server.S02PacketChat || event.getPacket() instanceof net.minecraft.network.play.server.S45PacketTitle) &&
                    !this.stopwatch.elapsed(6000L)) {
                event.setCancelled();
            }
        }
       else if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel")) {
            if (event.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
                NotificationManager.show(new Notification(NotificationType.WARNING, "Flag", "Disabled Fly due to flag.", 1));
                toggle();
            }
        } else if (this.mode.getValue() == Mode.DISABLER &&
                event.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
            NotificationManager.show(new Notification(NotificationType.WARNING, "Teleported", "Teleported due to LagBack", 1));
            if (!this.tp) {
                this.tp = true;
            }
        }
    }



    private void mineplexDamage(EntityPlayerSP playerRef) {
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        double offset = 0.060100000351667404D;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < PlayerUtils.getMaxFallDist() / 0.060100000351667404D + 1.0D; j++) {
                netHandler.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.060100000351667404D, mc.thePlayer.posZ, false));
                netHandler.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5.000000237487257E-4D, mc.thePlayer.posZ, false));
            }
        }
        netHandler.addToSendQueue((Packet)new C03PacketPlayer(true));
    }


    public void onEnabled() {
        this.arrayDeque.clear();
        this.tp = false;
        this.lastPos = new CustomVec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        EntityPlayerSP player = mc.thePlayer;
        if (this.mode.getValue() == Mode.MINEPLEX) {
            mineplexDamage(player);
        }
        if (this.mode.getValue() == Mode.DISABLER) {
            mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.18D, mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.08D, mc.thePlayer.posZ, true));
        }
        this.stopwatch.reset();
        this.y = 0.0D;
        this.lastDist = 0.0D;
        this.moveSpeed = 0.0D;
        this.stage = 0;
        this.ticks = 0;
        player.stepHeight = 0.0F;
        player.motionX = 0.0D;
        player.motionZ = 0.0D;

    }


    public void onDisabled() {
        EntityPlayerSP player = mc.thePlayer;
        mc.timer.timerSpeed = 1.0F;
        player.stepHeight = 0.625F;
        player.motionX = 0.0D;
        player.motionZ = 0.0D;
        if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel")) {
            player.setPosition(player.posX, player.posY + this.y, player.posZ);
        }
    }

    @EventTarget
    public final void onMove(MoveEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        GameSettings gameSettings = mc.gameSettings;
        switch ((Mode)this.mode.getValue()) {
            case WATCHDOG:
                if (MovementUtils.isMoving()) {
                    switch (this.stage) {
                        case 0:
                            if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                                PlayerUtils.damage();
                                this.moveSpeed = 0.5D * ((Double)this.speed.getValue()).doubleValue();
                            }
                            break;
                        case 1:
                            if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                                event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.39999994D);
                            }
                            this.moveSpeed *= 2.149D;
                            break;
                        case 2:
                            this.moveSpeed = 1.3D * ((Double)this.speed.getValue()).doubleValue();
                            break;
                        default:
                            this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
                            break;
                    }
                    MovementUtils.setSpeed(this.mc.thePlayer, Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed()));
                    this.stage++;
                }
                break;
            case MINEPLEX:
                event.y = player.motionY = 0.0D;
                if (this.stopwatch.elapsed(6100L)) {
                    MovementUtils.setSpeed(this.mc.thePlayer, 6.0D);

                    if (gameSettings.keyBindJump.isKeyDown()) {
                        event.y = player.motionY = 1.0D;
                    }
                    if (gameSettings.keyBindSneak.isKeyDown()) {
                        event.y = player.motionY = -1.0D;
                    }
                } else {
                    MovementUtils.setSpeed(this.mc.thePlayer, 0.0D);
                }
                if (this.stopwatch.elapsed(7000L)) {
                    MovementUtils.setSpeed(this.mc.thePlayer, 0.0D);
                    mineplexDamage(player);
                    this.stopwatch.reset();
                }
                break;
            case VANILLA:
                MovementUtils.setSpeed(this.mc.thePlayer, ((Double)this.vanillaSpeed.getValue()).intValue());
                break;
            case DISABLER:
                event.y = mc.thePlayer.motionY = 0.0D;
                if (this.tp) {

                    event.y = mc.thePlayer.motionY = 2.0D;
                    if (gameSettings.keyBindSneak.isKeyDown()) {
                        event.y = mc.thePlayer.motionY = -2.0D;
                    }
                    MovementUtils.setSpeed(this.mc.thePlayer, 2.0D); break;
                }
                MovementUtils.setSpeed(this.mc.thePlayer, 0.0D);
                break;

            case CUBECRAFT:
                mc.timer.timerSpeed = 1.0F;
                MovementUtils.setSpeed(this.mc.thePlayer, 0.0D);
                if (this.ticks > 12) {
                    if (this.stage == 4) {
                        MovementUtils.setSpeed(this.mc.thePlayer, 0.953532D);
                        this.stage = 0; break;
                    }
                    MovementUtils.setSpeed(this.mc.thePlayer, 0.121984218421847D);
                    break;
                }
                MovementUtils.setSpeed(this.mc.thePlayer, 0.0D);
                break;
        }
    }


    @EventTarget
    public final void onMotionUpdate(MotionUpdateEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        Timer timer = mc.timer;
        GameSettings gameSettings = mc.gameSettings;
        Stopwatch stopwatch = this.stopwatch;

        switch ((Mode)this.mode.getValue()) {
            case WATCHDOG:
                if (this.multiplier.getValue().booleanValue()) {
                    if (!stopwatch.elapsed(((Double)this.multiplytime.getValue()).longValue())) {
                        mc.timer.timerSpeed = ((Double)this.multiplyspeed.getValue()).floatValue();
                    } else {

                        mc.timer.timerSpeed = 1.0F;
                    }
                }
                if (event.isPre()) {
                    if (this.stage > 2)
                        player.motionY = 0.0D;
                    if (this.viewBobbing.getValue().booleanValue()) {
                        player.cameraYaw = 0.105F;
                    }
                    if (this.stage > 2) {
                        player.setPosition(player.posX, player.posY - 0.003D, player.posZ);
                        this.ticks++;
                        double offset = 3.25E-4D;
                        switch (this.ticks) {
                            case 1:
                                this.y *= -0.949999988079071D;
                                break;
                            case 2:
                            case 3:
                            case 4:
                                this.y += 3.25E-4D;
                                break;
                            case 5:
                                this.y += 5.0E-4D;
                                this.ticks = 0;
                                break;
                        }
                        event.setPosY(player.posY + this.y);
                    }  break;
                }
                if (this.stage > 2) {
                    player.setPosition(player.posX, player.posY + 0.003D, player.posZ);
                }
                break;

            case VANILLA:
                if (event.isPre()) {
                    player.motionY = 0.0D;
                    if (gameSettings.keyBindJump.isKeyDown()) {
                        player.motionY = 2.0D; break;
                    }  if (gameSettings.keyBindSneak.isKeyDown()) {
                        player.motionY = -2.0D;
                    }
                }
                break;


            case MINEPLEX:
                if (player.hurtTime == 9) {
                    stopwatch.reset();
                }
                if (stopwatch.elapsed(6000L) &&
                        !this.tp) {
                    NetHandlerPlayClient netHandler = mc.getNetHandler();
                    netHandler.addToSendQueue((Packet)new C0CPacketInput(0.0F, 0.0F, true, true));
                    CustomVec3 lastPos = this.lastPos;
                    ArrayList<CustomVec3> computePath = PathfindingUtils.computePath(new CustomVec3(player.posX, player.posY, player.posZ), lastPos);
                    for (int i = 0, computePathSize = computePath.size(); i < computePathSize; i++) {
                        CustomVec3 vec3 = computePath.get(i);
                        netHandler.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec3.getX(), vec3.getY(), vec3.getZ(), true));
                    }
                    NotificationManager.show(new Notification(NotificationType.INFO, "Fly", "Fly Exploit finished..", 1));
                    mc.thePlayer.setPosition(lastPos.getX(), lastPos.getY(), lastPos.getZ());
                    this.tp = true;
                }
                break;

            case CUBECRAFT:
                if (event.isPre()) {
                    timer.timerSpeed = 0.82F;
                    this.ticks++;
                    float y = (float)Math.floor(player.posY);
                    if (this.ticks == 1 && !player.onGround) {
                        toggle();
                    }
                    player.motionY = 0.0D;
                    if (this.ticks < 10) {
                        event.setPosY(y - 1.01D);
                        player.motionY = -0.101D; break;
                    }  if (this.ticks > 12) {
                        this.stage++;
                        switch (this.stage) {
                            case 1:
                                event.setPosY(y + 0.381D + MathUtils.randomNumber(0.03D, 0.05D));
                                break;
                            case 2:
                                event.setPosY(y + 0.355D + MathUtils.randomNumber(0.03D, 0.05D));
                                break;
                            case 3:
                                event.setPosY(y + 0.325D + MathUtils.randomNumber(0.03D, 0.05D));
                                break;
                            case 4:
                                event.setPosY(y + MathUtils.randomNumber(0.03D, 0.05D));
                                break;
                        }
                    }
                }
                break;
        }
        if (event.isPre()) {
            double xDif = player.posX - player.prevPosX;
            double zDif = player.posZ - player.prevPosZ;
            this.lastDist = Math.sqrt(xDif * xDif + zDif * zDif);
        }
    }

    public enum Mode {
        WATCHDOG, DISABLER, CUBECRAFT, MINEPLEX, VANILLA;
    }
}
