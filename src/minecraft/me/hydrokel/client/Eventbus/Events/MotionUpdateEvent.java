/*     */ package me.hydrokel.client.Eventbus.Events;
/*     */ 
/*     */
/*     */ public final class MotionUpdateEvent
/*     */   extends Cancellable
/*     */   implements Event {
/*     */   private double posX;
/*     */   private double lastPosX;
/*     */   private double posY;
/*     */   private double lastPosY;
/*     */   private double posZ;
/*     */   private double lastPosZ;
/*     */   
/*     */   public MotionUpdateEvent(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, Type type) {
/*  17 */     this.posX = posX;
/*  18 */     this.posY = posY;
/*  19 */     this.posZ = posZ;
/*  20 */     this.yaw = yaw;
/*  21 */     this.pitch = pitch;
/*  22 */     this.onGround = onGround;
/*  23 */     this.type = type;
/*     */   }
/*     */   private float yaw; private float lastYaw; private float pitch; private float lastPitch; private boolean onGround; private Type type;
/*     */   public boolean isPre() {
/*  27 */     return (this.type == Type.PRE);
/*     */   }
/*     */   
/*     */   public double getPosX() {
/*  31 */     return this.posX;
/*     */   }
/*     */   
/*     */   public void setPosX(double posX) {
/*  35 */     this.posX = posX;
/*     */   }
/*     */   
/*     */   public double getLastPosX() {
/*  39 */     return this.lastPosX;
/*     */   }
/*     */   
/*     */   public void setLastPosX(double lastPosX) {
/*  43 */     this.lastPosX = lastPosX;
/*     */   }
/*     */   
/*     */   public double getPosY() {
/*  47 */     return this.posY;
/*     */   }
/*     */   
/*     */   public void setPosY(double posY) {
/*  51 */     this.posY = posY;
/*     */   }
/*     */   
/*     */   public double getLastPosY() {
/*  55 */     return this.lastPosY;
/*     */   }
/*     */   
/*     */   public void setLastPosY(double lastPosY) {
/*  59 */     this.lastPosY = lastPosY;
/*     */   }
/*     */   
/*     */   public double getPosZ() {
/*  63 */     return this.posZ;
/*     */   }
/*     */   
/*     */   public void setPosZ(double posZ) {
/*  67 */     this.posZ = posZ;
/*     */   }
/*     */   
/*     */   public double getLastPosZ() {
/*  71 */     return this.lastPosZ;
/*     */   }
/*     */   
/*     */   public void setLastPosZ(double lastPosZ) {
/*  75 */     this.lastPosZ = lastPosZ;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  79 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public void setYaw(float yaw) {
/*  83 */     this.yaw = yaw;
/*     */   }
/*     */   
/*     */   public float getLastYaw() {
/*  87 */     return this.lastYaw;
/*     */   }
/*     */   
/*     */   public void setLastYaw(float lastYaw) {
/*  91 */     this.lastYaw = lastYaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  95 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public void setPitch(float pitch) {
/*  99 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */   public float getLastPitch() {
/* 103 */     return this.lastPitch;
/*     */   }
/*     */   
/*     */   public void setLastPitch(float lastPitch) {
/* 107 */     this.lastPitch = lastPitch;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 111 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public void setOnGround(boolean onGround) {
/* 115 */     this.onGround = onGround;
/*     */   }
/*     */   
/*     */   public Type getType() {
/* 119 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(Type type) {
/* 123 */     this.type = type;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 127 */     PRE, POST;
/*     */   }
/*     */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\events\player\MotionUpdateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */