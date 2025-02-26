package com.emperdog.bloodbag.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.emperdog.bloodbag.BloodBagMod;
import com.emperdog.bloodbag.config.BloodBagConfig;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.vampire.IBloodStats;
import de.teamlapen.vampirism.api.entity.player.vampire.IVampirePlayer;
import de.teamlapen.vampirism.core.ModFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BloodBagItem extends Item implements IBauble {

    public static int maxBlood = BloodBagConfig.maxBlood;
    public static int maxBloodFluid = maxBlood * VReference.FOOD_TO_FLUID_BLOOD;

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
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(BloodBagConfig.canFeedFromInventory && entity instanceof EntityPlayer)
            onWornTick(stack, (EntityPlayer) entity);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase entity) {
        if(
                //entity.world.isRemote
                entity.world.getWorldTime() % 100 != 0
                || !(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;
        IVampirePlayer vampirePlayer = getVampirePlayer(player);
        if(vampirePlayer == null)
            return;

        IBloodStats bloodStats = vampirePlayer.getBloodStats();
        if(bloodStats.needsBlood()) {
            IFluidHandlerItem cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if(cap == null || cap.drain(maxBloodFluid, false) == null)
                return;

            int toDrink = bloodStats.getMaxBlood() - bloodStats.getBloodLevel();
            vampirePlayer.drinkBlood(toDrink, IBloodStats.LOW_SATURATION);
            cap.drain(toDrink * VReference.FOOD_TO_FLUID_BLOOD, true);
            player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 0.2f, 1.3f);
        }
    }

    @Nullable
    public IVampirePlayer getVampirePlayer(EntityPlayer player) {
        if(VampirismAPI.factionRegistry().getFaction(player) != VReference.VAMPIRE_FACTION) {
            BloodBagMod.LOGGER.info("{} is not a vampire.", player.getName());
            return null;
        }

        IFactionPlayer<?> factionPlayer = VampirismAPI.getFactionPlayerHandler(player).getCurrentFactionPlayer();
        return factionPlayer instanceof IVampirePlayer ? (IVampirePlayer) factionPlayer : null;
    }

    public int getCurrentBlood(ItemStack stack) {
        IFluidHandlerItem cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return cap != null && cap.drain(maxBloodFluid, false) != null
                ? cap.drain(maxBloodFluid, false).amount / VReference.FOOD_TO_FLUID_BLOOD
                : 0;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        IBlockState b = world.getBlockState(pos);
        return b.getBlock().hasTileEntity(b) && world.getTileEntity(pos).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        double currentBlood = (double) getCurrentBlood(stack) / maxBlood;
        return 1.0 - currentBlood;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0xFF0000;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.isEmpty())
            return new FluidHandlerItemStack(stack, maxBloodFluid) {
                @Override
                public boolean canFillFluidType(FluidStack fluid) {
                    return fluid.getFluid() == ModFluids.blood;
                }

                @Override
                public boolean canDrainFluidType(FluidStack fluid) {
                    return fluid.getFluid() == ModFluids.blood;
                }
            };
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(fluidHandler != null) {
            int blood = 0;
            if(fluidHandler.drain(1, false) != null)
                blood = fluidHandler.drain(maxBloodFluid, false).amount / VReference.FOOD_TO_FLUID_BLOOD;
            tooltip.add(I18n.format("item.bloodbag.blood_bag.tooltip", blood, maxBlood));
        }
    }
}
