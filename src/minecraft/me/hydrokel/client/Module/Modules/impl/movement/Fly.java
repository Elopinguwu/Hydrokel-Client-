package me.hydrokel.client.Module.Modules.impl.movement;

import de.Hero.settings.Setting;
import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventBoudingBox;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Eventbus.Events.EventUpdate;
import me.hydrokel.client.Main;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.Utils.PlayerUtils;
import me.hydrokel.client.notifications.Notification;
import me.hydrokel.client.notifications.NotificationManager;
import me.hydrokel.client.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;

import java.util.ArrayList;

public class Fly extends Module {

	public Fly() {

		super("Fly", Keyboard.KEY_F, Category.MOVEMENT);

	}

	@Override
	public void onEnable() {
		NotificationManager.show(new Notification(NotificationType.INFO, "Fly", "You toggled Fly.", 1));

		super.onEnable();
		if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("DamageFly")) {

			okmec = true;
			state = false;
		}

		MovementUtils.Damage();
	}
	protected int state2 = 0;
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1F;
		EntityPlayerSP player = mc.thePlayer;
		player.stepHeight = 0.625F;
		mc.thePlayer.motionY = 0F;
		mc.thePlayer.motionX = 0F;
		mc.thePlayer.motionZ = 0F;
		NotificationManager.show(new Notification(NotificationType.INFO, "Fly", "You untoggled Fly.", 1));
	//	if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel"))
		this.state2 = 0;
		super.onDisable();

		state = false;

	}

	private boolean state = false;
	private boolean okmec;

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("DamageFly");
		options.add("CubecraftInfinite");
		options.add("Cubecraft15Seconds");
		options.add("Hypixel");

		Main.instance.setmgr.rSetting(new Setting("Fly Mode", this, "Vanilla", options));

	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vanilla")) {
			mc.thePlayer.motionY = mc.gameSettings.keyBindSneak.pressed ? (mc.gameSettings.keyBindJump.pressed ? 0.42 : -0.42) : (mc.gameSettings.keyBindJump.pressed ? 0.42 : 0);
			if (MovementUtils.isMoving())
				MovementUtils.setSpeed(1f);

		}

				if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("CubecraftInfinite")) {
					mc.timer.timerSpeed = 0.24F;
					if (MovementUtils.isMoving()) {
						MovementUtils.setSpeed(0.38);
						if (mc.thePlayer.ticksExisted % 3 == 0) {
							mc.thePlayer.motionY = 0.25;
							MovementUtils.setSpeed(2.65);
						} else {
							mc.thePlayer.motionY -= 0.15;
						}
					}

				}


			}



	@EventTarget
	public void ptdrtg(EventMotion anarghtquisklid) {
		if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("DamageFly")) {

			if (mc.thePlayer.hurtTime > 0 && !state) {
				mc.thePlayer.motionY = mc.gameSettings.keyBindSneak.pressed ? (mc.gameSettings.keyBindJump.pressed ? 0.42 : -0.42) : (mc.gameSettings.keyBindJump.pressed ? 0.42 : 0);
				if (MovementUtils.isMoving())
					MovementUtils.setSpeed(1f);
			} else {

			}

		}
		if (Main.instance.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Cubecraft15Seconds")) {
			mc.timer.timerSpeed = 0.31F;
			if (MovementUtils.isMoving()) {
				MovementUtils.setSpeed(0.40);
				if (mc.thePlayer.ticksExisted % 4 == 0) {
					mc.thePlayer.motionY = 0.25;
					MovementUtils.setSpeed(3.20);
				} else {
					mc.thePlayer.motionY -= 0.15;

				}
			}

		}
	}
}









