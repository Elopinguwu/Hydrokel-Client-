/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class Option<T>
/*    */ {
/*    */   private final String label;
/*    */   private T value;
/*    */   private final Supplier<Boolean> supplier;
/*    */   
/*    */   public Option(String label, T defaultValue, Supplier<Boolean> supplier) {
/* 12 */     this.label = label;
/* 13 */     this.value = defaultValue;
/* 14 */     this.supplier = supplier;
/*    */   }
/*    */   
/*    */   public boolean check() {
/* 18 */     return ((Boolean)this.supplier.get()).booleanValue();
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 22 */     return this.label;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 26 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(T value) {
/* 30 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\module\option\Option.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */