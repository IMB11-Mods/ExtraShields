package dev.imb11.shields.compat.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;

import java.util.List;

public class AnvilViewRecipe implements ReliableClientRecipe {
    private final SlotContent left;
    private final SlotContent right;
    private final SlotContent result;

    public AnvilViewRecipe(AnvilServerRecipe modRecipe) {
        this.left = SlotContent.of(modRecipe.getLeft());
        this.right = SlotContent.of(modRecipe.getRight());
        this.result = SlotContent.of(modRecipe.getResult());
    }

    @Override
    public ReliableClientRecipeType getViewType() {
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