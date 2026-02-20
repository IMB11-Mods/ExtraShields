package dev.imb11.shields.compat.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import dev.imb11.shields.Shields;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;


public class AnvilViewType implements ReliableClientRecipeType {

    public static final AnvilViewType INSTANCE = new AnvilViewType();

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.shields.anvil");
    }

    @Override
    public int getDisplayWidth() {
        return 100;
    }

    @Override
    public int getDisplayHeight() {
        return 25;
    }

    @Override
    public Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(Shields.MOD_ID, "textures/gui/anvil_combining.png");
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 5, 4);
        slotDefinition.addItemSlot(1, 41, 4);
        slotDefinition.addItemSlot(2, 78, 4);
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath("shields", "anvil_combining");
    }

    @Override
    public ItemStack getIcon() {
        return Items.ANVIL.getDefaultInstance();
    }

    public List<ItemStack> getCraftReferences() {
        return List.of(Items.ANVIL.getDefaultInstance());
    }


}