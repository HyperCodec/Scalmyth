package dev.nyxane.mods.scalmyth.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import terrablender.api.ParameterUtils.*;

import java.util.List;
import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder overlayBuilder = new VanillaParameterOverlayBuilder();

        // there's probably a way to make the biomes spawn
        // at deterministic points here.
        List<Climate.ParameterPoint> paramPoints = new ParameterPointListBuilder()
                .temperature(Temperature.span(Temperature.NEUTRAL, Temperature.HOT))
                .humidity(Humidity.span(Humidity.ARID, Humidity.NEUTRAL))
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .depth(ParameterUtils.Depth.SURFACE, Depth.FLOOR)
                .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
                .build();

        paramPoints.forEach(point -> overlayBuilder.add(point, ModBiomes.ASHEN_FOREST));

        overlayBuilder.build().forEach(mapper);

        // simpler way
//        this.addBiomeSimilar(mapper, Biomes.FOREST, ModBiomes.ASHEN_FOREST);
    }
}
