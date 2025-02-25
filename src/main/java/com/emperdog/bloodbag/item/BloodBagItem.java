package com.emperdog.bloodbag.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.vampire.IVampirePlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nullable;

public class BloodBagItem extends Item implements IBauble {

    public static int maxBlood = 15000;

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.HEAD;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if(VampirismAPI.factionRegistry().getFaction(player) != VReference.VAMPIRE_FACTION)
            return EnumActionResult.FAIL;

        IFactionPlayer<?> factionPlayer = VampirismAPI.getFactionPlayerHandler(player).getCurrentFactionPlayer();
        if(!(factionPlayer instanceof IVampirePlayer)) return EnumActionResult.FAIL;

        IVampirePlayer vampirePlayer = (IVampirePlayer) factionPlayer;

        if(vampirePlayer.getBloodStats().needsBlood())
            return EnumActionResult.SUCCESS;
        else
            return EnumActionResult.FAIL;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        IBlockState b = world.getBlockState(pos);
        return b.getBlock().hasTileEntity(b) && world.getTileEntity(pos).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

    @Nullable
    @Override
    public Item getContainerItem() {
        return super.getContainerItem();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.isEmpty())
            return new FluidHandlerItemStack(stack, maxBlood);
        return null;
    }
}
