package com.emperdog.bloodbag;

import com.emperdog.bloodbag.bloodbag.Tags;
import com.emperdog.bloodbag.item.BloodBagItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class BloodBagMod {

    public static BloodBagItem BLOOD_BAG_ITEM;

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     * Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);
    }


    @Mod.EventBusSubscriber
    public static class RegistryHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            LOGGER.info("fired RegistryEvent.Register<Item>");
            IForgeRegistry<Item> registry = event.getRegistry();

            BLOOD_BAG_ITEM = registerItem(registry, new BloodBagItem(), "blood_bag");
        }

        public static <T extends Item> T registerItem(IForgeRegistry<Item> registry, T item, String name) {
            item.setTranslationKey(Tags.MOD_ID + "." + name);
            item.setRegistryName(new ResourceLocation(Tags.MOD_ID, name));
            registry.register(item);
            return item;
        }

    }
}
