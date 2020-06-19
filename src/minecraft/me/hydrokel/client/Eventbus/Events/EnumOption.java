/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */
/*    */ public final class EnumOption<T extends Enum>
/*    */   extends Option<T>
/*    */ {
/*    */   private final T[] values;
/*    */   
/*    */   public EnumOption(String label, T defaultValue, Supplier<Boolean> supplier) {
/* 12 */     super(label, defaultValue, supplier);
/*    */     
/* 14 */     this.values = (T[])((Enum)getValue()).getClass().getEnumConstants();
/*    */   }
/*    */   
/*    */   public EnumOption(String label, T defaultValue) {
/* 18 */     super(label, defaultValue, () -> Boolean.valueOf(true));
/*    */     
/* 20 */     this.values = (T[])((Enum)getValue()).getClass().getEnumConstants();
/*    */   }
/*    */   
/*    */   public T getValueOrNull(String constantName) {
/* 24 */     for (T value : this.values) {
/* 25 */       if (value.name().equals(constantName)) {
/* 26 */         return value;
/*    */       }
/*    */     } 
/*    */     
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public final T[] getValues() {
/* 34 */     return this.values;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\module\option\impl\EnumOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */