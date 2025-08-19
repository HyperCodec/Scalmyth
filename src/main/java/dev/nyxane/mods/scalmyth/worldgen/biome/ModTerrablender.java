package dev.nyxane.mods.scalmyth.worldgen.biome;

import dev.nyxane.mods.scalmyth.api.ScalmythAPI;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static final ResourceLocation OVERWORLD = ResourceLocation.fromNamespaceAndPath(ScalmythAPI.MOD_ID, "overworld");

    // this could probably belong to registry but whatever
    public static void init() {
        Regions.register(new ModOverworldRegion(OVERWORLD, 1));
    }
}
