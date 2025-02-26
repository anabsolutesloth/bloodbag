package com.emperdog.bloodbag.integration;

import baubles.api.BaubleType;
import com.emperdog.bloodbag.BloodBagMod;
import com.emperdog.bloodbag.config.BloodBagConfig;

import java.util.Arrays;

public class BaublesIntegration {

    public static BaubleType bloodBagSlot = BaubleType.TRINKET;

    static {
        String slotName = BloodBagConfig.bloodBagSlot.toUpperCase();
        if(Arrays.stream(BaubleType.class.getEnumConstants())
                .anyMatch(baubleType -> baubleType.name().equals(slotName))) {
            bloodBagSlot = BaubleType.valueOf(slotName);
        } else {
            BloodBagMod.LOGGER.error("Slot type '{}' is not a valid Bauble Slot type! defaulting to 'TRINKET' type.", slotName);
        }
    }
}
