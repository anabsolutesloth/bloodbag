package com.emperdog.bloodbag.mixin;

import baubles.api.BaublesApi;
import com.emperdog.bloodbag.BloodBagMod;
import de.teamlapen.vampirism.fluids.BloodHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//@Mixin(BloodHelper.class)
public abstract class BloodHelperMixin {

    /*
    @Inject(method = "getBloodContainerInHotbar(Lnet/minecraft/entity/player/InventoryPlayer;)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"), cancellable = true)
    public static void bloodbag_getBloodBagInBauble(InventoryPlayer inventory, CallbackInfoReturnable<ItemStack> callback) {
        int bloodBagSlot = BaublesApi.isBaubleEquipped(inventory.player, BloodBagMod.BLOOD_BAG_ITEM);
        if(bloodBagSlot == -1) return;

        ItemStack bloodBag = BaublesApi.getBaublesHandler(inventory.player).getStackInSlot(bloodBagSlot);
        callback.setReturnValue(bloodBag);
    }
     */
}