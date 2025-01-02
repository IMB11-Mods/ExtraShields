package dev.imb11.shields.enchantments;

import dev.imb11.shields.datagen.providers.ShieldsEnchantmentProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ShieldsEnchantmentEffects {
    public static InteractionResult eventShieldBlock(LivingEntity livingEntity, DamageSource damageSource, float amount, InteractionHand interactionHand, ItemStack shield) {
        if (shield.isEnchanted()) {
            var enchantmentRegistry = livingEntity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            // Check for our enchantments.
            int evokeringLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ShieldsEnchantmentProvider.EVOKERING), shield);
            if (evokeringLevel > 0 && damageSource.getEntity() instanceof LivingEntity attackerEntity) {
                EvokeringEnchantmentEffect.handle((ServerLevel) livingEntity.level(), evokeringLevel, attackerEntity, livingEntity);
            }
        }

        return InteractionResult.PASS;
    }
}
