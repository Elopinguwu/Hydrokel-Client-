/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */ import java.util.function.Supplier;

/*    */ 
/*    */ public final class DoubleOption
/*    */   extends Option<Double>
/*    */ {
/*    */   private final double minValue;
/*    */   private final double maxValue;
/*    */   private final double increment;
/*    */   
/*    */   public DoubleOption(String label, double defaultValue, Supplier<Boolean> supplier, double minValue, double maxValue, double increment) {
/* 14 */     super(label, Double.valueOf(defaultValue), supplier);
/* 15 */     this.minValue = minValue;
/* 16 */     this.maxValue = maxValue;
/* 17 */     this.increment = increment;
/*    */   }
/*    */   
/*    */   public DoubleOption(String label, double defaultValue, double minValue, double maxValue, double increment) {
/* 21 */     super(label, Double.valueOf(defaultValue), () -> Boolean.valueOf(true));
/* 22 */     this.minValue = minValue;
/* 23 */     this.maxValue = maxValue;
/* 24 */     this.increment = increment;
/*    */   }
/*    */   
/*    */   public double getMinValue() {
/* 28 */     return this.minValue;
/*    */   }
/*    */   
/*    */   public double getMaxValue() {
/* 32 */     return this.maxValue;
/*    */   }
/*    */   
/*    */   public double getIncrement() {
/* 36 */     return this.increment;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\module\option\impl\DoubleOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */