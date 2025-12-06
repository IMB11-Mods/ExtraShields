package dev.imb11.shields.compat.eiv;

import de.crafty.eiv.common.api.recipe.IEivRecipeViewType;
import de.crafty.eiv.common.api.recipe.IEivViewRecipe;
import de.crafty.eiv.common.recipe.inventory.RecipeViewMenu;
import de.crafty.eiv.common.recipe.inventory.SlotContent;
import dev.imb11.shields.compat.eiv.AnvilServerRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import java.util.List;

public class AnvilViewRecipe implements IEivViewRecipe {
    private final SlotContent left;
    private final SlotContent right;
    private final SlotContent result;

    public AnvilViewRecipe(AnvilServerRecipe modRecipe) {
        this.left = SlotContent.of(modRecipe.getLeft());
        this.right = SlotContent.of(modRecipe.getRight());
        this.result = SlotContent.of(modRecipe.getResult());
    }

    @Override
    public IEivRecipeViewType getViewType() {
        return AnvilViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        slotFillContext.bindOptionalSlot(0, left, RecipeViewMenu.OptionalSlotRenderer.DEFAULT);
        slotFillContext.bindOptionalSlot(1, right, RecipeViewMenu.OptionalSlotRenderer.DEFAULT);
        slotFillContext.bindOptionalSlot(2, result, RecipeViewMenu.OptionalSlotRenderer.DEFAULT);
    }

    @Override
    public List<SlotContent> getIngredients() {
        return List.of(left, right);
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(result);
    }
}