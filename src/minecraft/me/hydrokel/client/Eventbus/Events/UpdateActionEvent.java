/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */
/*    */ public final class UpdateActionEvent
/*    */   implements Event {
/*    */   private boolean sprintState;
/*    */   private boolean sneakState;
/*    */   
/*    */   public UpdateActionEvent(boolean sprintState, boolean sneakState) {
/* 11 */     this.sprintState = sprintState;
/* 12 */     this.sneakState = sneakState;
/*    */   }
/*    */   
/*    */   public boolean isSprintState() {
/* 16 */     return this.sprintState;
/*    */   }
/*    */   
/*    */   public void setSprintState(boolean sprintState) {
/* 20 */     this.sprintState = sprintState;
/*    */   }
/*    */   
/*    */   public boolean isSneakState() {
/* 24 */     return this.sneakState;
/*    */   }
/*    */   
/*    */   public void setSneakState(boolean sneakState) {
/* 28 */     this.sneakState = sneakState;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\events\player\UpdateActionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */