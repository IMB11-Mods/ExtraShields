//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.Shields;
import dev.imb11.shields.items.ShieldCollection;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;

import java.util.concurrent.CompletableFuture;

public class ShieldsRecipeProvider extends FabricRecipeProvider {
    public ShieldsRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> bootstrapContext, BootstrapContext<Advancement> bootstrapContext1) {
        return new RecipeProvider(bootstrapContext, bootstrapContext1) {
            @Override
            public void buildRecipes() {
                netheriteSmithing(ShieldsItems.DIAMOND.plating(), RecipeCategory.COMBAT, ShieldsItems.NETHERITE.plating());

                SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ShieldsItems.DIAMOND.shieldItem()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.COMBAT, ShieldsItems.NETHERITE.shieldItem()).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                        .save(output, ShieldsItems.NETHERITE.shieldItemKey().identifier().withSuffix("_smithing").toString());

                var conditionedRecipeOutput = withConditions(output, ResourceConditions.not(ResourceConditions.anyModsLoaded("shields-mxsv", "lolmsv")));

                shieldPlating(Items.IRON_INGOT, ItemTags.IRON_TOOL_MATERIALS, ShieldsItems.IRON.plating());

                shieldPlating(Items.GOLD_INGOT, ItemTags.GOLD_TOOL_MATERIALS, ShieldsItems.GOLD.plating());

                shieldPlating(Items.DIAMOND, ItemTags.DIAMOND_TOOL_MATERIALS, ShieldsItems.DIAMOND.plating());

                shieldPlating(Items.COPPER_INGOT, ItemTags.COPPER_TOOL_MATERIALS, ShieldsItems.COPPER.plating());

                shield(Items.GOLD_INGOT, ItemTags.GOLD_TOOL_MATERIALS, ShieldsItems.GOLD.shieldItem(), conditionedRecipeOutput);

                shield(Items.DIAMOND, ItemTags.DIAMOND_TOOL_MATERIALS, ShieldsItems.DIAMOND.shieldItem(), conditionedRecipeOutput);

                shield(Items.COPPER_INGOT, ItemTags.COPPER_TOOL_MATERIALS, ShieldsItems.COPPER.shieldItem(), conditionedRecipeOutput);

                shaped(RecipeCategory.COMBAT, ShieldsItems.SHIELD_REPAIR_KIT, 1)
                        .define('i', Items.IRON_INGOT)
                        .define('s', Items.STICK)
                        .pattern(" i ")
                        .pattern("isi")
                        .pattern(" i ")
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(output);

                for (ShieldCollection shieldCollection : ShieldsItems.SHIELD_COLLECTIONS.values()) {
                    decoration(shieldCollection.shieldItem(), shieldCollection.shieldItemKey());
                    decoration(shieldCollection.platedShieldItem(), shieldCollection.platedShieldItemKey());
                }
            }

			private void shieldPlating(Item goldIngot, TagKey<Item> material, Item shieldItem) {
                shaped(RecipeCategory.COMBAT, shieldItem, 1)
                        .define('p', ItemTags.PLANKS)
                        .define('m', material)
                        .pattern("mpm")
                        .pattern("ppp")
                        .pattern("mpm")
                        .unlockedBy(getHasName(goldIngot), has(goldIngot))
                        .group("shield_platings")
                        .save(output);
			}

            private void decoration(Item shieldItem, ResourceKey<Item> id) {
                if (id.identifier().getNamespace().equals(Shields.MOD_ID)) {
                    SpecialRecipeBuilder.special(
                                    () -> new ShieldDecorationRecipe(this.tag(ItemTags.BANNERS), Ingredient.of(shieldItem), new ItemStackTemplate(shieldItem))
                            )
                            .save(this.output, id.identifier().getPath()+"_decoration");
                }
            }

            private void shield(Item material, ShieldItem shieldItem, RecipeOutput conditionedRecipeOutput) {
                shaped(RecipeCategory.COMBAT, shieldItem, 1)
                        .define('d', material)
                        .define('w', ItemTags.PLANKS)
                        .pattern("wdw")
                        .pattern("www")
                        .pattern(" w ")
                        .unlockedBy(getHasName(material), has(material))
                        .save(conditionedRecipeOutput);
            }

            private void shield(Item material, TagKey<Item> ingredient, ShieldItem shieldItem, RecipeOutput conditionedRecipeOutput) {
                shaped(RecipeCategory.COMBAT, shieldItem, 1)
                        .define('d', ingredient)
                        .define('w', ItemTags.PLANKS)
                        .pattern("wdw")
                        .pattern("www")
                        .pattern(" w ")
                        .unlockedBy(getHasName(material), has(material))
                        .save(conditionedRecipeOutput);
            }
        };
    }

    @Override
    public String getName() {
        return "extrashields";
    }
}
//?}