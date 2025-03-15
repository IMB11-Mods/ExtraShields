package dev.imb11.shields.compat;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.data.RecipeDefaults;
import dev.imb11.shields.items.ShieldsItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class EmiCompat implements EmiPlugin {
    public static class EmiAnvilCombineRecipe implements EmiRecipe {
        private final EmiStack input1;
        private final EmiStack input2;
        private final EmiStack output;
        private final ResourceLocation id;
        private final int uniq;

        public EmiAnvilCombineRecipe(EmiStack input1, EmiStack input2, EmiStack output, ResourceLocation id) {
            this.uniq = EmiUtil.RANDOM.nextInt();
            this.input1 = input1;
            this.input2 = input2;
            this.output = output;
            this.id = id;
        }

        public EmiRecipeCategory getCategory() {
            return VanillaEmiRecipeCategories.ANVIL_REPAIRING;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public List<EmiIngredient> getInputs() {
            return List.of(this.input1, this.input2);
        }

        public List<EmiStack> getOutputs() {
            return List.of(this.output);
        }

        public boolean supportsRecipeTree() {
            return false;
        }

        public int getDisplayWidth() {
            return 125;
        }

        public int getDisplayHeight() {
            return 18;
        }

        public void addWidgets(WidgetHolder widgets) {
            widgets.addTexture(EmiTexture.PLUS, 27, 3);
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 75, 1);
            widgets.addGeneratedSlot((r) -> this.input1, this.uniq, 0, 0);
            widgets.addSlot(this.input2, 49, 0);
            widgets.addGeneratedSlot((r) -> this.output, this.uniq, 107, 0).recipeContext(this);
        }
    }


    @Override
    public void register(EmiRegistry emiRegistry) {
        for (Map.Entry<Item, Item[]> itemEntry : ShieldsItems.PLATING_UPGRADE_MAP.entrySet()) {
            Item shield = itemEntry.getValue()[0];
            Item plating = itemEntry.getKey();
            Item platedShield = itemEntry.getValue()[1];

            emiRegistry.addRecipe(new EmiAnvilCombineRecipe(
                EmiStack.of(new ItemStack(shield)),
                EmiStack.of(new ItemStack(plating)),
                EmiStack.of(new ItemStack(platedShield)),
                ResourceLocation.tryBuild("shields", "/plating_upgrade_" + BuiltInRegistries.ITEM.getKey(platedShield).getPath() + "_" + BuiltInRegistries.ITEM.getKey(platedShield).getPath())
            ));
        }
    }
}
