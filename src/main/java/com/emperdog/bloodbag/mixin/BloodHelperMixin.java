package com.emperdog.bloodbag.mixin;

import baubles.api.BaublesApi;
import com.emperdog.bloodbag.BloodBagMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.teamlapen.vampirism.fluids.BloodHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BloodHelper.class)
public abstract class BloodHelperMixin {

    @WrapOperation(method = "fillBloodIntoInventory", remap = false,
            at = @At(value = "INVOKE",
                    target = "getBloodContainerInHotbar(Lnet/minecraft/entity/player/InventoryPlayer;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack bloodbag_fillBloodIntoBaubles(InventoryPlayer inventory, Operation<ItemStack> original, @Local(argsOnly = true) EntityPlayer player) {
        int bloodBagSlot = BaublesApi.isBaubleEquipped(player, BloodBagMod.BLOOD_BAG_ITEM);
        //BloodBagMod.LOGGER.info("thinking about filling blood bag, maybe in slot {}?", bloodBagSlot);
        if(bloodBagSlot == -1) return original.call(inventory);

        //BloodBagMod.LOGGER.info("filling blood bag maybe?");
        return BaublesApi.getBaublesHandler(player).getStackInSlot(bloodBagSlot);
    }
}