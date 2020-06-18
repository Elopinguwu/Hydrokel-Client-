/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */
/*    */ public final class BoolOption
/*    */   extends Option<Boolean>
/*    */ {
/*    */   public BoolOption(String label, Boolean defaultValue, Supplier<Boolean> supplier) {
/* 10 */     super(label, defaultValue, supplier);
/*    */   }
/*    */   
/*    */   public BoolOption(String label, Boolean defaultValue) {
/* 14 */     super(label, defaultValue, () -> Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean getValue() {
/* 19 */     return Boolean.valueOf((((Boolean)super.getValue()).booleanValue() && check()));
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\module\option\impl\BoolOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */