package dev.imb11.shields.compat.eiv;

import de.crafty.eiv.common.api.recipe.EivRecipeType;
import de.crafty.eiv.common.api.recipe.IEivServerRecipe;
import de.crafty.eiv.common.recipe.util.EivTagUtil;
import dev.imb11.shields.Shields;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AnvilServerRecipe implements IEivServerRecipe {

    public static final EivRecipeType<AnvilServerRecipe> TYPE = EivRecipeType.register(
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
        tag.put("left", EivTagUtil.encodeItemStackOnServer(left));
        tag.put("right", EivTagUtil.encodeItemStackOnServer(right));
        tag.put("result",  EivTagUtil.encodeItemStackOnServer(result));
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        left = EivTagUtil.decodeItemStackOnClient(tag.getCompoundOrEmpty("left"));
        right = EivTagUtil.decodeItemStackOnClient(tag.getCompoundOrEmpty("right"));
        result =  EivTagUtil.decodeItemStackOnClient(tag.getCompoundOrEmpty("result"));
    }

    @Override
    public EivRecipeType<? extends IEivServerRecipe> getRecipeType() {
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