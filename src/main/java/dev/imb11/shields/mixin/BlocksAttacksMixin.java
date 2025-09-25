package dev.imb11.shields.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlocksAttacks.class)
public class BlocksAttacksMixin {

    @WrapOperation(method = "disable", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/ItemStack;I)V"))
    public void hurt(ItemCooldowns instance, ItemStack stack, int original, Operation<Void> operation, @Local ServerLevel level, @Local LivingEntity entity) {
        var enchantmentLookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        // Check for bracing enchantment, each level decreases cooldown ticks by 10%.
        if (stack != null) {
            var enchantment = enchantmentLookup.getOrThrow(ShieldsEnchantmentKeys.BRACING);
            int enchantmentLevel = stack.getEnchantments().getLevel(enchantment);

            if (enchantmentLevel > 0) {
                int i = (int) (original * (1 - (0.1 * enchantmentLevel)));
                operation.call(instance, stack, i);
            }
        }

        operation.call(instance, stack, original);
    }
}
