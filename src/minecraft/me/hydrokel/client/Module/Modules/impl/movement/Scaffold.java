package me.hydrokel.client.Module.Modules.impl.movement;

import me.hydrokel.client.Eventbus.EventTarget;
import me.hydrokel.client.Eventbus.Events.EventMotion;
import me.hydrokel.client.Module.Category;
import me.hydrokel.client.Module.Module;
import me.hydrokel.client.Utils.MovementUtils;
import me.hydrokel.client.Utils.RotationsUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold",0, Category.MOVEMENT);
    }
    @EventTarget
    public void onMotion(EventMotion e) {
        AxisAlignedBB box = this.mc.thePlayer.getEntityBoundingBox();
        AxisAlignedBB ajusted_box = box.offset(0, -0.5, 0).expand(-0.001, 0, -0.001);
         Stream<AxisAlignedBB> block_collisions = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, ajusted_box).stream();
        if (block_collisions.findAny().isPresent()) {
            return;
        }

            BlockPos p_block = this.getProximityBlock(this.mc.thePlayer.);
        if (p_block == null) {
            return;
        }
            Vec3 block_vector = new Vec3(p_block);
            RotationsUtils.Rotation needed_rotations = RotationsUtils.getNeededRotations(block_vector);
            e.setYaw(needed_rotations.getYaw());
            e.setPitch(100);
            //OOF
            this.mc.playerController.onPlayerRightClick(this.mc.thePlayer,this.mc.theWorld,this.mc.thePlayer.getHeldItem(),p_block, this.mc.thePlayer.getHorizontalFacing(),block_vector);

    }
    public BlockPos getProximityBlock(EntityPlayer e) {
        Random r = new Random();
        double p_x = e.posX;
        double p_z = e.posY;
        BlockPos block_out;
        List<BlockPos> block_arround = new ArrayList<BlockPos>();
        for (int x = 0;x < 3;x++) {
            for (int y = 0;y < 3;y++) {
                BlockPos temp_block = new BlockPos(p_x - 1 + x , e.posY - 1,p_z - 1 + y);
                if (this.mc.theWorld.getBlockState(temp_block).getBlock().getMaterial() != Material.air) {
                    block_arround.add(temp_block);
                }
            }
        }


        System.out.println(block_arround);
        if (block_arround.size() > 0) {
            return block_arround.get(0);
        } else {
            return null;
        }
       // return new BlockPos(e.posX,e.posY-1,e.posZ);
    }
}
