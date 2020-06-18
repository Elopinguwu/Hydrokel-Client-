/*     */ package me.hydrokel.client.Utils;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public final class PlayerUtils
/*     */ {
/*  22 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   public static boolean isHoldingSword() {
/*  25 */     return (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemSword);
/*     */   }
/*     */   
/*     */   public static boolean isOnSameTeam(EntityPlayer entity) {
/*  29 */     if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
/*  30 */       char c1 = entity.getDisplayName().getFormattedText().charAt(1);
/*  31 */       char c2 = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
/*  32 */       return (c1 == c2);
/*     */     } 
/*     */     
/*  35 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isInLiquid() {
/*  39 */     boolean inLiquid = false;
/*  40 */     AxisAlignedBB playerBB = mc.thePlayer.getEntityBoundingBox();
/*  41 */     int y = (int)playerBB.minY;
/*  42 */     for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; x++) {
/*  43 */       for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; z++) {
/*  44 */         Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
/*  45 */         if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
/*  46 */           if (!(block instanceof net.minecraft.block.BlockLiquid)) {
/*  47 */             return false;
/*     */           }
/*  49 */           inLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*  53 */     return inLiquid;
/*     */   }
/*     */   
/*     */   public static boolean isOnLiquid() {
/*  57 */     boolean onLiquid = false;
/*  58 */     AxisAlignedBB playerBB = mc.thePlayer.getEntityBoundingBox();
/*  59 */     WorldClient world = mc.theWorld;
/*  60 */     int y = (int)(playerBB.offset(0.0D, -0.01D, 0.0D)).minY;
/*  61 */     for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; x++) {
/*  62 */       for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; z++) {
/*  63 */         Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
/*  64 */         if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
/*  65 */           if (!(block instanceof net.minecraft.block.BlockLiquid)) {
/*  66 */             return false;
/*     */           }
/*  68 */           onLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*  72 */     return onLiquid;
/*     */   }
/*     */   
/*     */   public static boolean isOnHypixel() {
/*  76 */     return (!mc.isSingleplayer() && (mc.getCurrentServerData()).serverIP.contains("hypixel"));
/*     */   }
/*     */   
/*     */   public static void damage() {
/*  80 */     double offset = 0.060100000351667404D;
/*  81 */     NetHandlerPlayClient netHandler = mc.getNetHandler();
/*  82 */     EntityPlayerSP player = mc.thePlayer;
/*  83 */     double x = player.posX;
/*  84 */     double y = player.posY;
/*  85 */     double z = player.posZ;
/*  86 */     for (int i = 0; i < getMaxFallDist() / 0.05510000046342611D + 1.0D; i++) {
/*  87 */       netHandler.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404D, z, false));
/*  88 */       netHandler.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4D, z, false));
/*  89 */       netHandler.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
/*     */     } 
/*  91 */     netHandler.addToSendQueue((Packet)new C03PacketPlayer(true));
/*     */   }
/*     */   
/*     */   public static float getMaxFallDist() {
/*  95 */     PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
/*  96 */     int f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0;
/*  97 */     return (mc.thePlayer.getMaxFallHeight() + f);
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnder() {
/* 101 */     EntityPlayerSP player = mc.thePlayer;
/* 102 */     WorldClient world = mc.theWorld;
/* 103 */     AxisAlignedBB pBb = player.getEntityBoundingBox();
/* 104 */     double height = player.posY + player.getEyeHeight();
/* 105 */     for (int offset = 0; offset < height; offset += 2) {
/* 106 */       if (!world.getCollidingBoundingBoxes((Entity)player, pBb.offset(0.0D, -offset, 0.0D)).isEmpty()) {
/* 107 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isInsideBlock() {
/* 115 */     EntityPlayerSP player = mc.thePlayer;
/* 116 */     WorldClient world = mc.theWorld;
/* 117 */     AxisAlignedBB bb = player.getEntityBoundingBox();
/* 118 */     for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; x++) {
/* 119 */       for (int y = MathHelper.floor_double(bb.minY); y < MathHelper.floor_double(bb.maxY) + 1; y++) {
/* 120 */         for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ) {
/*     */           
/* 122 */           Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock(); AxisAlignedBB boundingBox;
/* 123 */           if (block == null || block instanceof net.minecraft.block.BlockAir || (boundingBox = block.getCollisionBoundingBox((World)world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)))) == null || 
/* 124 */             !player.getEntityBoundingBox().intersectsWith(boundingBox)) { z++; continue; }
/* 125 */            return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autum\\utils\PlayerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */