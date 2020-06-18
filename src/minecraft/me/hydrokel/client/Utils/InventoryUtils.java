/*    */ package me.hydrokel.client.Utils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.ItemSword;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public final class InventoryUtils {
/* 12 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static void swap(int slot, int hotBarSlot) {
/* 15 */     mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotBarSlot, 2, (EntityPlayer)mc.thePlayer);
/*    */   }
/*    */   
/*    */   public static boolean isValidItem(ItemStack itemStack) {
/* 19 */     if (itemStack.getDisplayName().startsWith("§a")) {
/* 20 */       return true;
/*    */     }
/* 22 */     return (itemStack.getItem() instanceof net.minecraft.item.ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack
/* 23 */       .getItem() instanceof net.minecraft.item.ItemTool || itemStack.getItem() instanceof net.minecraft.item.ItemFood || (itemStack
/* 24 */       .getItem() instanceof ItemPotion && !isBadPotion(itemStack)) || itemStack.getItem() instanceof net.minecraft.item.ItemBlock || itemStack
/* 25 */       .getDisplayName().contains("Play") || itemStack.getDisplayName().contains("Game") || itemStack
/* 26 */       .getDisplayName().contains("Right Click"));
/*    */   }
/*    */   
/*    */   public static float getDamageLevel(ItemStack stack) {
/* 30 */     if (stack.getItem() instanceof ItemSword) {
/* 31 */       ItemSword sword = (ItemSword)stack.getItem();
/* 32 */       float sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F;
/* 33 */       float fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1.5F;
/* 34 */       return sword.getDamageVsEntity() + sharpness + fireAspect;
/*    */     } 
/*    */     
/* 37 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public static boolean isBadPotion(ItemStack stack) {
/* 41 */     if (stack != null && stack.getItem() instanceof ItemPotion) {
/* 42 */       ItemPotion potion = (ItemPotion)stack.getItem();
/*    */       
/* 44 */       if (ItemPotion.isSplash(stack.getItemDamage())) {
/* 45 */         for (Object o : potion.getEffects(stack)) {
/* 46 */           PotionEffect effect = (PotionEffect)o;
/*    */           
/* 48 */           if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
/* 49 */             return true;
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hytykateur\Desktop\Autumn.jar!\rip\autum\\utils\InventoryUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */