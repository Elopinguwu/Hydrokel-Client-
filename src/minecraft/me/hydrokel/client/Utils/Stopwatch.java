/*    */ package me.hydrokel.client.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Stopwatch
/*    */ {
/*  8 */   private long ms = getCurrentMS();
/*    */ 
/*    */   
/*    */   private long getCurrentMS() {
/* 12 */     return System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public final long getElapsedTime() {
/* 16 */     return getCurrentMS() - this.ms;
/*    */   }
/*    */   
/*    */   public final boolean elapsed(long milliseconds) {
/* 20 */     return (getCurrentMS() - this.ms > milliseconds);
/*    */   }
/*    */   
/*    */   public final void reset() {
/* 24 */     this.ms = getCurrentMS();
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autum\\utils\Stopwatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */