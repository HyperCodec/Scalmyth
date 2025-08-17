package dev.nyxane.mods.scalmyth.worldgen.biome.surface;

import dev.nyxane.mods.scalmyth.registry.ModBlocks;
import dev.nyxane.mods.scalmyth.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

public class ModSurfaceRules {
    public static final RuleSource GRASS_BLOCK = defaultStateRule(Blocks.GRASS_BLOCK);
    public static final RuleSource DIRT = defaultStateRule(Blocks.DIRT);
    public static final RuleSource ASHEN_GRASS_BLOCK = defaultStateRule(ModBlocks.ASHEN_GRASS.get());

    public static RuleSource makeRules() {
        SurfaceRules.ConditionSource higherThanWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(higherThanWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.ASHEN_FOREST),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ASHEN_GRASS_BLOCK)
                                )
                        )
                ),

                // default to grass and dirt
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }


    public static RuleSource defaultStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
