package dev.nyxane.mods.scalmyth.worldgen.biome;

import dev.nyxane.mods.scalmyth.api.ScalmythAPI;
import dev.nyxane.mods.scalmyth.worldgen.ModPlacedFeatures;
import net.minecraft.client.particle.AshParticle;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {
    public static final ResourceKey<Biome> ASHEN_FOREST = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(ScalmythAPI.MOD_ID, "ashen_forest"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(ASHEN_FOREST, ashenForest(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    private static Biome ashenForest(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
        );

        // we can remove this method and configure things manually.
        // I just wanted to start with something simple though.
        globalOverworldGeneration(settings);
        BiomeDefaultFeatures.addDefaultOres(settings);

        settings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.ASHEN_TREE_PLACED);

        BiomeSpecialEffects.Builder specialFx = new BiomeSpecialEffects.Builder()
                .waterColor(0xFF0000)
                .fogColor(0x404040)
                .waterFogColor(0x3B2626)
                .skyColor(0x757575)
                .ambientParticle(new AmbientParticleSettings(
                        ParticleTypes.ASH,
                        0.25f
                ));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(settings.build())
                .specialEffects(specialFx.build())
                .mobSpawnSettings(spawnSettings.build())
                .build();
    }
}
