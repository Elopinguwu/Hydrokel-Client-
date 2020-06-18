/*     */ package me.hydrokel.client.Utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AStarCustomPathFinder
/*     */ {
/*  14 */   private static final Minecraft MC = Minecraft.getMinecraft();
/*  15 */   private static final CustomVec3[] flatCardinalDirections = new CustomVec3[] { new CustomVec3(1.0D, 0.0D, 0.0D), new CustomVec3(-1.0D, 0.0D, 0.0D), new CustomVec3(0.0D, 0.0D, 1.0D), new CustomVec3(0.0D, 0.0D, -1.0D) };
/*     */ 
/*     */   
/*     */   private final CustomVec3 startCustomVec3;
/*     */ 
/*     */   
/*     */   private final CustomVec3 endCustomVec3;
/*     */   
/*  23 */   private final ArrayList<Hub> hubs = new ArrayList<>();
/*  24 */   private final ArrayList<Hub> hubsToWork = new ArrayList<>();
/*  25 */   private ArrayList<CustomVec3> path = new ArrayList<>();
/*     */   
/*     */   public AStarCustomPathFinder(CustomVec3 startCustomVec3, CustomVec3 endCustomVec3) {
/*  28 */     this.startCustomVec3 = startCustomVec3.addVector(0.0D, 0.0D, 0.0D).floor();
/*  29 */     this.endCustomVec3 = endCustomVec3.addVector(0.0D, 0.0D, 0.0D).floor();
/*     */   }
/*     */   
/*     */   public static boolean checkPositionValidity(CustomVec3 vec3) {
        BlockPos pos = new BlockPos(vec3.getZ(),vec3.getY(),vec3.getZ());
/*  34 */     if (isBlockSolid(pos) || isBlockSolid(pos.add(0, 1, 0))) return false; 
/*  35 */     return isSafeToWalkOn(pos.add(0, -1, 0));
/*     */   }
/*     */   
/*     */   private static boolean isBlockSolid(BlockPos pos) {
/*  39 */     Block block = MC.theWorld.getBlockState(pos).getBlock();
/*  40 */     return (block.isSolidFullCube() || block instanceof net.minecraft.block.BlockSlab || block instanceof net.minecraft.block.BlockStairs || block instanceof net.minecraft.block.BlockCactus || block instanceof net.minecraft.block.BlockChest || block instanceof net.minecraft.block.BlockEnderChest || block instanceof net.minecraft.block.BlockSkull || block instanceof net.minecraft.block.BlockPane || block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockGlass || block instanceof net.minecraft.block.BlockPistonBase || block instanceof net.minecraft.block.BlockPistonExtension || block instanceof net.minecraft.block.BlockPistonMoving || block instanceof net.minecraft.block.BlockStainedGlass || block instanceof net.minecraft.block.BlockTrapDoor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSafeToWalkOn(BlockPos pos) {
/*  59 */     Block block = MC.theWorld.getBlockState(pos).getBlock();
/*  60 */     return (!(block instanceof net.minecraft.block.BlockFence) && !(block instanceof net.minecraft.block.BlockWall));
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<CustomVec3> getPath() {
/*  65 */     return this.path;
/*     */   }
/*     */   
/*     */   public void compute() {
/*  69 */     compute(1000, 4);
/*     */   }
/*     */   
/*     */   public void compute(int loops, int depth) {
/*  73 */     this.path.clear();
/*  74 */     this.hubsToWork.clear();
/*  75 */     ArrayList<CustomVec3> initPath = new ArrayList<>();
/*  76 */     CustomVec3 startCustomVec3 = this.startCustomVec3;
/*  77 */     initPath.add(startCustomVec3);
/*  78 */     CustomVec3[] flatCardinalDirections = AStarCustomPathFinder.flatCardinalDirections;
/*  79 */     this.hubsToWork.add(new Hub(startCustomVec3, null, initPath, startCustomVec3.squareDistanceTo(this.endCustomVec3), 0.0D, 0.0D));
/*     */     int i;
/*  81 */     label38: for (i = 0; i < loops; i++) {
/*  82 */       ArrayList<Hub> hubsToWork = this.hubsToWork;
/*  83 */       int hubsToWorkSize = hubsToWork.size();
/*  84 */       hubsToWork.sort(new CompareHub());
/*  85 */       int j = 0;
/*  86 */       if (hubsToWorkSize == 0) {
/*     */         break;
/*     */       }
/*  89 */       for (int i1 = 0; i1 < hubsToWorkSize; i1++) {
/*  90 */         Hub hub = hubsToWork.get(i1);
/*  91 */         j++;
/*  92 */         if (j > depth) {
/*     */           break;
/*     */         }
/*  95 */         hubsToWork.remove(hub);
/*  96 */         this.hubs.add(hub);
/*  97 */         CustomVec3 hLoc = hub.getLoc();
/*     */         
/*  99 */         for (int i2 = 0, flatCardinalDirectionsLength = flatCardinalDirections.length; i2 < flatCardinalDirectionsLength; i2++) {
/* 100 */           CustomVec3 loc = hLoc.add(flatCardinalDirections[i2]).floor();
/* 101 */           if (checkPositionValidity(loc) && 
/* 102 */             addHub(hub, loc, 0.0D)) {
/*     */             break label38;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 108 */         CustomVec3 loc1 = hLoc.addVector(0.0D, 1.0D, 0.0D).floor();
/* 109 */         if (checkPositionValidity(loc1) && 
/* 110 */           addHub(hub, loc1, 0.0D)) {
/*     */           break label38;
/*     */         }
/*     */ 
/*     */         
/* 115 */         CustomVec3 loc2 = hLoc.addVector(0.0D, -1.0D, 0.0D).floor();
/* 116 */         if (checkPositionValidity(loc2) && 
/* 117 */           addHub(hub, loc2, 0.0D)) {
/*     */           break label38;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     this.hubs.sort(new CompareHub());
/* 126 */     this.path = ((Hub)this.hubs.get(0)).getPath();
/*     */   }
/*     */   
/*     */   public Hub isHubExisting(CustomVec3 loc) {
/* 130 */     List<Hub> hubs = this.hubs;
/* 131 */     for (int i = 0, hubsSize = hubs.size(); i < hubsSize; i++) {
/* 132 */       Hub hub = hubs.get(i);
/* 133 */       CustomVec3 hubLoc = hub.getLoc();
/* 134 */       if (hubLoc.getX() == loc.getX() && hubLoc.getY() == loc.getY() && hubLoc.getZ() == loc.getZ()) {
/* 135 */         return hub;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     List<Hub> hubsToWork = this.hubsToWork;
/* 140 */     for (int j = 0, hubsToWorkSize = hubsToWork.size(); j < hubsToWorkSize; j++) {
/* 141 */       Hub hub = hubsToWork.get(j);
/* 142 */       CustomVec3 hubLoc = hub.getLoc();
/* 143 */       if (hubLoc.getX() == loc.getX() && hubLoc.getY() == loc.getY() && hubLoc.getZ() == loc.getZ()) {
/* 144 */         return hub;
/*     */       }
/*     */     } 
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   public boolean addHub(Hub parent, CustomVec3 loc, double cost) {
/* 151 */     Hub existingHub = isHubExisting(loc);
/* 152 */     double totalCost = cost;
/* 153 */     if (parent != null) {
/* 154 */       totalCost += parent.getTotalCost();
/*     */     }
/* 156 */     CustomVec3 endCustomVec3 = this.endCustomVec3;
/* 157 */     ArrayList<CustomVec3> parentPath = parent.getPath();
/* 158 */     if (existingHub == null) {
/* 159 */       if (loc.getX() == endCustomVec3.getX() && loc.getY() == endCustomVec3.getY() && loc.getZ() == endCustomVec3.getZ()) {
/* 160 */         this.path.clear();
/* 161 */         this.path = parentPath;
/* 162 */         this.path.add(loc);
/* 163 */         return true;
/*     */       } 
/* 165 */       parentPath.add(loc);
/* 166 */       this.hubsToWork.add(new Hub(loc, parent, parentPath, loc.squareDistanceTo(endCustomVec3), cost, totalCost));
/*     */     }
/* 168 */     else if (existingHub.getCost() > cost) {
/* 169 */       parentPath.add(loc);
/* 170 */       existingHub.setLoc(loc);
/* 171 */       existingHub.setParent(parent);
/* 172 */       existingHub.setPath(parentPath);
/* 173 */       existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(endCustomVec3));
/* 174 */       existingHub.setCost(cost);
/* 175 */       existingHub.setTotalCost(totalCost);
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   private static class Hub {
/*     */     private CustomVec3 loc;
/*     */     private Hub parent;
/*     */     private ArrayList<CustomVec3> path;
/*     */     private double squareDistanceToFromTarget;
/*     */     private double cost;
/*     */     private double totalCost;
/*     */     
/*     */     public Hub(CustomVec3 loc, Hub parent, ArrayList<CustomVec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
/* 189 */       this.loc = loc;
/* 190 */       this.parent = parent;
/* 191 */       this.path = path;
/* 192 */       this.squareDistanceToFromTarget = squareDistanceToFromTarget;
/* 193 */       this.cost = cost;
/* 194 */       this.totalCost = totalCost;
/*     */     }
/*     */     
/*     */     public CustomVec3 getLoc() {
/* 198 */       return this.loc;
/*     */     }
/*     */     
/*     */     public void setLoc(CustomVec3 loc) {
/* 202 */       this.loc = loc;
/*     */     }
/*     */     
/*     */     public Hub getParent() {
/* 206 */       return this.parent;
/*     */     }
/*     */     
/*     */     public void setParent(Hub parent) {
/* 210 */       this.parent = parent;
/*     */     }
/*     */     
/*     */     public ArrayList<CustomVec3> getPath() {
/* 214 */       return this.path;
/*     */     }
/*     */     
/*     */     public void setPath(ArrayList<CustomVec3> path) {
/* 218 */       this.path = path;
/*     */     }
/*     */     
/*     */     public double getSquareDistanceToFromTarget() {
/* 222 */       return this.squareDistanceToFromTarget;
/*     */     }
/*     */     
/*     */     public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
/* 226 */       this.squareDistanceToFromTarget = squareDistanceToFromTarget;
/*     */     }
/*     */     
/*     */     public double getCost() {
/* 230 */       return this.cost;
/*     */     }
/*     */     
/*     */     public void setCost(double cost) {
/* 234 */       this.cost = cost;
/*     */     }
/*     */     
/*     */     public double getTotalCost() {
/* 238 */       return this.totalCost;
/*     */     }
/*     */     
/*     */     public void setTotalCost(double totalCost) {
/* 242 */       this.totalCost = totalCost;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CompareHub
/*     */     implements Comparator<Hub> {
/*     */     public int compare(AStarCustomPathFinder.Hub o1, AStarCustomPathFinder.Hub o2) {
/* 249 */       return 
/* 250 */         (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - o2.getSquareDistanceToFromTarget() + o2.getTotalCost());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autum\\utils\pathfinding\AStarCustomPathFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */