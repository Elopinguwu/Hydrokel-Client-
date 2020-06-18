/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */ public class Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public boolean isCancelled() {
/*  8 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 12 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled() {
/* 16 */     this.cancelled = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\events\Cancellable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */