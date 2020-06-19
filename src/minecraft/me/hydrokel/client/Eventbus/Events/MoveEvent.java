/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */
/*    */ public final class MoveEvent
/*    */   implements Event {
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   
/*    */   public MoveEvent(double x, double y, double z) {
/* 12 */     this.x = x;
/* 13 */     this.y = y;
/* 14 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\events\player\MoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */