package com.emperdog.bloodbag.config;

import com.emperdog.bloodbag.bloodbag.Tags;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public class BloodBagConfig {

    @Config.Comment({
            "Maximum Blood the Blood Bag can hold.",
            "default: [80]"
    })
    public static int maxBlood = 80;

    @Config.Comment({
            "If the Blood Bag can autofeed from your Inventory, instead of just Bauble slots.",
            "default: [false]"
    })
    public static boolean canFeedFromInventory = false;
}
