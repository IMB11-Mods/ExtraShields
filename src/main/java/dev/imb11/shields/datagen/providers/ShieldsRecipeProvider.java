package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ShieldsRecipeProvider extends FabricRecipeProvider {
    public ShieldsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void netheriteSmithing(RecipeOutput recipeOutput, Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(new ItemLike[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}), Ingredient.of(new ItemLike[]{ingredientItem}), Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}), category, resultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(recipeOutput, getItemName(resultItem) + "_smithing");
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        netheriteSmithing(recipeOutput, ShieldsItems.DIAMOND_SHIELD_PLATING, RecipeCategory.COMBAT, ShieldsItems.NETHERITE_SHIELD_PLATING);

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(new ItemLike[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}), Ingredient.of(new ItemLike[]{ShieldsItems.DIAMOND_SHIELD}), Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}), RecipeCategory.COMBAT, ShieldsItems.NETHERITE_SHIELD).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(withConditions(recipeOutput, ResourceConditions.not(ResourceConditions.anyModsLoaded("shields-mxsv", "lolmsv"))), getItemName(ShieldsItems.NETHERITE_SHIELD) + "_smithing");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.SHIELD_PLATING, 1)
                .define('c', ItemTags.PLANKS)
                .define('i', Items.IRON_INGOT)
                .pattern("ici")
                .pattern("ccc")
                .pattern("ici")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .group("shield_platings")
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.DIAMOND_SHIELD_PLATING, 1)
                .define('c', ItemTags.PLANKS)
                .define('d', Items.DIAMOND)
                .pattern("dcd")
                .pattern("ccc")
                .pattern("dcd")
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .group("shield_platings")
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.COPPER_SHIELD_PLATING, 1)
                .define('c', ItemTags.PLANKS)
                .define('p', Items.COPPER_INGOT)
                .pattern("pcp")
                .pattern("ccc")
                .pattern("pcp")
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .group("shield_platings")
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.GOLD_SHIELD_PLATING, 1)
                .define('c', ItemTags.PLANKS)
                .define('g', Items.GOLD_INGOT)
                .pattern("gcg")
                .pattern("ccc")
                .pattern("gcg")
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .group("shield_platings")
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.GOLD_SHIELD, 1)
                .define('g', Items.GOLD_INGOT)
                .define('w', ItemTags.PLANKS)
                .pattern("wgw")
                .pattern("www")
                .pattern(" w ")
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.DIAMOND_SHIELD, 1)
                .define('d', Items.DIAMOND)
                .define('w', ItemTags.PLANKS)
                .pattern("wdw")
                .pattern("www")
                .pattern(" w ")
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.COPPER_SHIELD, 1)
                .define('c', Items.COPPER_INGOT)
                .define('w', ItemTags.PLANKS)
                .define('h', Items.HONEYCOMB)
                .pattern("wcw")
                .pattern("whw")
                .pattern(" w ")
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldsItems.SHIELD_REPAIR_KIT, 1)
                .define('i', Items.IRON_INGOT)
                .define('s', Items.STICK)
                .pattern(" i ")
                .pattern("isi")
                .pattern(" i ")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);
    }
}
