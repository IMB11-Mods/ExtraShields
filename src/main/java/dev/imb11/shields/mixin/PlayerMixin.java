package dev.imb11.shields.mixin;

import dev.imb11.shields.items.BannerShieldItemWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(at = @At(value = "HEAD"), method = "disableShield")
    public void addShieldContext(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack activeItemStack = player.getUseItem();

        if (activeItemStack.getItem() instanceof BannerShieldItemWrapper wrapper) {
            wrapper.MIXIN$ITEM_STACK_VALUE = activeItemStack;
            wrapper.ENCHANTMENT_LOOKUP = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        }
    }
}

