package dev.nyxane.mods.scalmyth.datagen;

import dev.nyxane.mods.scalmyth.api.ScalmythAPI;
import dev.nyxane.mods.scalmyth.registry.ModBlocks;
import dev.nyxane.mods.scalmyth.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//UNUSED
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        planksFromLog(recipeOutput, ModItems.ASHEN_PLANKS.get(), ModItems.TAG_LOGS, 4);
        fenceBuilder(ModItems.ASHEN_FENCE, Ingredient.of(ModItems.ASHEN_PLANKS))
                .group("ashen")
                .unlockedBy("has_ashen",has(ModItems.ASHEN_LOG))
                .save(recipeOutput);
        fenceGateBuilder(ModItems.ASHEN_FENCE_GATE, Ingredient.of(ModItems.ASHEN_PLANKS))
                .group("ashen")
                .unlockedBy("has_ashen",has(ModItems.ASHEN_LOG))
                .save(recipeOutput);
        stairBuilder(ModBlocks.ASHEN_STAIR.get(), Ingredient.of(ModItems.ASHEN_PLANKS))
                .group("ashen")
                .unlockedBy("has_ashen",has(ModItems.ASHEN_LOG)).save(recipeOutput);
        pressurePlate(recipeOutput, ModItems.ASHEN_PRESSURE_PLATE, ModItems.ASHEN_PLANKS);
        buttonBuilder(ModItems.ASHEN_BUTTON, Ingredient.of(ModItems.ASHEN_PLANKS))
                .group("ashen")
                .unlockedBy("has_ashen", has(ModItems.ASHEN_PLANKS))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModItems.ASHEN_SLAB, ModItems.ASHEN_PLANKS);
    }

    protected static void foodCooking(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        cooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
        cooking(recipeOutput, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme/2, pGroup, "_from_blasting");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        cooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
        cooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme/2, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void cooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ScalmythAPI.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
