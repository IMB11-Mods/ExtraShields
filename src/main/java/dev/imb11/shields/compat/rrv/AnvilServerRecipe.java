package dev.imb11.shields.compat.rrv;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import dev.imb11.shields.Shields;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AnvilServerRecipe implements ReliableServerRecipe {

    public static final ReliableServerRecipeType<AnvilServerRecipe> TYPE = ReliableServerRecipeType.register(
            ResourceLocation.fromNamespaceAndPath(Shields.MOD_ID,"anvil_combining"),
            () -> new AnvilServerRecipe(null, null, null)
    );
    private ItemStack left;
    private ItemStack right;
    private ItemStack result;

    public AnvilServerRecipe(ItemStack left, ItemStack right, ItemStack result) {
        this.left = left;
        this.right = right;
        this.result = result;
    }


    @Override
    public void writeToTag(CompoundTag tag) {
        tag.put("left", TagUtil.encodeItemStackOnServer(left));
        tag.put("right", TagUtil.encodeItemStackOnServer(right));
        tag.put("result", TagUtil.encodeItemStackOnServer(result));
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        left = TagUtil.decodeItemStackOnClient(tag.getCompoundOrEmpty("left"));
        right = TagUtil.decodeItemStackOnClient(tag.getCompoundOrEmpty("right"));
        result = TagUtil.decodeItemStackOnClient(tag.getCompoundOrEmpty("result"));
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }

    public ItemStack getLeft() {
        return left;
    }

    public ItemStack getRight() {
        return right;
    }

    public ItemStack getResult() {
        return result;
    }
}