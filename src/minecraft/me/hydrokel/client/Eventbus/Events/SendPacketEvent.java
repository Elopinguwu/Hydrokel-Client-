/*    */ package me.hydrokel.client.Eventbus.Events;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */
/*    */ public final class SendPacketEvent
/*    */   extends Cancellable implements Event {
/*    */   private final Packet<?> packet;
/*    */   
/*    */   public SendPacketEvent(Packet<?> packet) {
/* 12 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public Packet<?> getPacket() {
/* 16 */     return this.packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autumn\events\packet\SendPacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */