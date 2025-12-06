package dev.imb11.shields.mixin;

import dev.imb11.shields.items.ShieldsItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ShieldDecorationRecipe.class)
public abstract class ShieldDecorationRecipeMixin extends CustomRecipe {


    public ShieldDecorationRecipeMixin(CraftingBookCategory category) {
        super(category);
    }

    /**
     * Credits to <a href="https://github.com/pnk2u/More-Shield-Variants/blob/1.21.5(-8)/src/main/java/de/pnku/lolmsv/mixin/recipe/ShieldDecorationRecipeMixin.java">More Shield Variant's Implementation of Shield Rendering</a>
     * for the correct mixin target.
     */
    @Inject(method = "matches(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/world/level/Level;)Z", at = @At("HEAD"), cancellable = true)
    private void injectedMatches(CraftingInput craftingInput, Level level, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); ++i) {
            ItemStack itemStack3 = craftingInput.getItem(i);
            if (itemStack3.isEmpty()) continue;
            if (itemStack3.getItem() instanceof BannerItem) {
                if (!itemStack2.isEmpty()) {
                    cir.setReturnValue(false);
                }
                itemStack2 = itemStack3;
                continue;
            }
            if (itemStack3.is(ShieldsItemTags.SHIELDS)) {
                if (!itemStack.isEmpty()) {
                    cir.setReturnValue(false);
                }
                BannerPatternLayers bannerPatternsComponent = itemStack3.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
                if (!bannerPatternsComponent.layers().isEmpty()) {
                    cir.setReturnValue(false);
                }
                itemStack = itemStack3;
                continue;
            }
            cir.setReturnValue(false);
        }
        cir.setReturnValue(!itemStack.isEmpty() && !itemStack2.isEmpty());
    }

    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void injectedAssemble(CraftingInput craftingInput, HolderLookup.Provider provider, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); ++i) {
            ItemStack itemStack3 = craftingInput.getItem(i);
            if (itemStack3.isEmpty()) continue;
            if (itemStack3.getItem() instanceof BannerItem) {
                itemStack = itemStack3;
                continue;
            }
            if (!(itemStack3.is(ShieldsItemTags.SHIELDS))) continue;
            itemStack2 = itemStack3.copy();
        }
        if (itemStack2.isEmpty()) {
            cir.setReturnValue(itemStack2);
        }
        itemStack2.set(DataComponents.BANNER_PATTERNS, itemStack.get(DataComponents.BANNER_PATTERNS));
        itemStack2.set(DataComponents.BASE_COLOR, ((BannerItem)itemStack.getItem()).getColor());
        cir.setReturnValue(itemStack2);
    }

}