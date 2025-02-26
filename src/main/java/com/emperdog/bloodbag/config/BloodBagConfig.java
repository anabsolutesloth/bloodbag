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
            "Ignored when Baubles is not present. Configure 'doesAutofeed' in those cases.",
            "default: [false]"
    })
    public static boolean canAutofeedFromInventory = true;

    @Config.Comment({
            "If the Blood Bag can even Autofeed in the first place.",
            "A Drinking behavior is enabled if this is false.",
            "default: [true]"
    })
    public static boolean doesAutofeed = true;

    @Config.Comment({
            "Maximum Blood that can be consumed at once from the Blood Bag.",
            "default: [5]"
    })
    public static int maxBloodPerDrink = 5;

    @Config.Comment({
            "Bauble slot the Blood Bag goes into.",
            "Use 'trinket' for any. Case does not matter.",
            "accepted: [amulet, ring, belt, head, body, charm, trinket]",
            "default: [trinket]"
    })
    public static String bloodBagSlot = "trinket";
}
