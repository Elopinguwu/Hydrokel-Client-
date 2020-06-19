/*    */ package me.hydrokel.client.Utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PathfindingUtils
/*    */ {
/* 16 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static ArrayList<CustomVec3> computePath(CustomVec3 topFrom, CustomVec3 to) {
/* 19 */     if (!canPassThrow(new BlockPos(topFrom.mc()))) {
/* 20 */       topFrom = topFrom.addVector(0.0D, 1.0D, 0.0D);
/*    */     }
/* 22 */    AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
/* 23 */    pathfinder.compute();
/*    */     
/* 25 */     int i = 0;
/* 26 */     CustomVec3 lastLoc = null;
/* 27 */     CustomVec3 lastDashLoc = null;
/* 28 */     ArrayList<CustomVec3> path = new ArrayList<>();
/* 29 */     ArrayList<CustomVec3> pathFinderPath = pathfinder.getPath();
/* 30 */     label42: for (CustomVec3 pathElm : pathFinderPath) {
/* 31 */       if (i == 0 || i == pathFinderPath.size() - 1) {
/* 32 */         if (lastLoc != null) {
/* 33 */           path.add(lastLoc.addVector(0.5D, 0.0D, 0.5D));
/*    */         }
/* 35 */         path.add(pathElm.addVector(0.5D, 0.0D, 0.5D));
/* 36 */         lastDashLoc = pathElm;
/*    */       } else {
/* 38 */         boolean canContinue = true;
/* 39 */         if (pathElm.squareDistanceTo(lastDashLoc) > 25.0D) {
/* 40 */           canContinue = false;
/*    */         } else {
/* 42 */           double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
/* 43 */           double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
/* 44 */           double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
/* 45 */           double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
/* 46 */           double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
/* 47 */           double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
/*    */           
/* 49 */           for (int x = (int)smallX; x <= bigX; x++) {
/* 50 */             for (int y = (int)smallY; y <= bigY; y++) {
/* 51 */               for (int z = (int)smallZ; z <= bigZ; z++) {
/* 52 */                 if (!AStarCustomPathFinder.checkPositionValidity(new CustomVec3(x, y, z))) {
/* 53 */                   canContinue = false;
/*    */                   continue label42;
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } 
/* 60 */         if (!canContinue) {
/* 61 */           path.add(lastLoc.addVector(0.5D, 0.0D, 0.5D));
/* 62 */           lastDashLoc = lastLoc;
/*    */         } 
/*    */       } 
/* 65 */       lastLoc = pathElm;
/* 66 */       i++;
/*    */     } 
/* 68 */     return path;
/*    */   }
/*    */   
/*    */   private static boolean canPassThrow(BlockPos pos) {
/* 72 */     Block block = mc.theWorld.getBlockState(pos).getBlock();
/* 73 */     return (block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign);
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autum\\utils\pathfinding\PathfindingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */