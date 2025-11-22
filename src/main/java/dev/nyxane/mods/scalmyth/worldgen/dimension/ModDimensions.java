package dev.nyxane.mods.scalmyth.worldgen.dimension;

import dev.nyxane.mods.scalmyth.api.ScalmythAPI;
import dev.nyxane.mods.scalmyth.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> ASHEN_STEM = ResourceKey.create(Registries.LEVEL_STEM, ScalmythAPI.rl("ashen_dimension"));
    public static final ResourceKey<Level> ASHEN_LEVEL = ResourceKey.create(Registries.DIMENSION, ScalmythAPI.rl("ashen_dimension"));
    public static final ResourceKey<DimensionType> ASHEN_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ScalmythAPI.rl("ashen"));

    public static void bootstrapType(BootstrapContext<DimensionType> ctx) {
        ctx.register(ASHEN_DIM_TYPE, new DimensionType(
                OptionalLong.empty(),
                false,
                false,
                false,
                false,
                1.0,
                false,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> ctx) {
        HolderGetter<Biome> biomeRegistry = ctx.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTyRegistry = ctx.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenRegistry = ctx.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator chunkGen = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.ASHEN_FOREST)),
                noiseGenRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
        );

        LevelStem stem = new LevelStem(dimTyRegistry.getOrThrow(ModDimensions.ASHEN_DIM_TYPE), chunkGen);
        ctx.register(ASHEN_STEM, stem);
    }
}
