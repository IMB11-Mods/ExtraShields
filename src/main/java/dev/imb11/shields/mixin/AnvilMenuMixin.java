package dev.imb11.shields.mixin;

import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow
    @Final
    private DataSlot cost;

    @Shadow @Nullable private String itemName;

    protected AnvilMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    /**
     * Credits to <a href="https://github.com/hiisuuii/infinicore/blob/master/src%2Fmain%2Fjava%2Fhisui%2Finfinicore%2Fmixin%2FInfinicoreMixin.java#L27">Infinicore's Implementation of Anvil Recipes</a>
     * for the correct mixin target.
     */
    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamageableItem()Z", ordinal = 0), cancellable = true)
    private void inject(CallbackInfo info) {
        // [plating (input2)], [input1, output]
        var recipeMap = ShieldsItems.PLATING_UPGRADE_MAP;

        ItemStack expectedInput1 = this.inputSlots.getItem(0);
        ItemStack expectedPlating = this.inputSlots.getItem(1);

        for (Map.Entry<Item, Item[]> itemEntry : recipeMap.entrySet()) {
            var plating = itemEntry.getKey();
            var input1 = itemEntry.getValue()[0];
            var output = itemEntry.getValue()[1];

            // Check if the input items are the same as the expected items
            if (expectedInput1.getItem() == input1 && expectedPlating.getItem() == plating) {
                this.resultSlots.setItem(0, expectedInput1.transmuteCopy(output));
                this.itemName = expectedInput1.getHoverName().getString();
                this.cost.set(1);
                info.cancel();
                return;
            }
        }
    }
}
