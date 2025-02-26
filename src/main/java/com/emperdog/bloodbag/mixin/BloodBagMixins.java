package com.emperdog.bloodbag.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

public class BloodBagMixins implements ILateMixinLoader {

    private static final List<String> notMods = ImmutableList.of("mixins", "json");

    @Override
    public List<String> getMixinConfigs() {
        return ImmutableList.of("mixins.baubles.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return Arrays.stream(mixinConfig.split("\\."))
                .filter(string -> !notMods.contains(string))
                .allMatch(Loader::isModLoaded);
    }
}
