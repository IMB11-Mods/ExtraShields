package dev.imb11.shields.mixin;

import dev.imb11.shields.items.ShieldsItems;
import dev.imb11.shields.items.custom.ShieldPatchKitItem;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean is(TagKey<Item> tag);

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void prioritizeShieldRepairKit(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (this.is(ConventionalItemTags.SHIELD_TOOLS)) {
            // Check if alternate hand has a Shield Repair Kit
            InteractionHand opposite = usedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack oppositeStack = player.getItemInHand(opposite);

            if (oppositeStack.getItem() instanceof ShieldPatchKitItem kitItem) {
                // Swap hands
                kitItem.mixin$did_flip_priorities = true;
                cir.setReturnValue(kitItem.use(level, player, opposite));
            }
        }
    }
}
