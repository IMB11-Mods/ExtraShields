package dev.imb11.shields.enchantments;

import dev.imb11.shields.datagen.providers.ShieldsEnchantmentProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShieldsEnchantmentEffects {
    public static InteractionResult eventShieldBlock(LivingEntity livingEntity, DamageSource damageSource, float amount, InteractionHand interactionHand, ItemStack shield) {
        if (shield.isEnchanted()) {
            var enchantmentRegistry = livingEntity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            // Check for our enchantments.
            int evokeringLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ShieldsEnchantmentProvider.EVOKERING), shield);
            if (evokeringLevel > 0 && damageSource.getEntity() instanceof LivingEntity attackerEntity) {
                Handlers.handleEvokering((ServerLevel) livingEntity.level(), evokeringLevel, attackerEntity, livingEntity);
            }

            int lifeboundLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ShieldsEnchantmentProvider.LIFEBOUND), shield);
            if (lifeboundLevel > 0 && damageSource.getEntity() instanceof LivingEntity attackerEntity) {
                Handlers.handleLifebound(lifeboundLevel, (Player) livingEntity, attackerEntity);
            }
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult eventShieldDisabled(Player player, InteractionHand interactionHand, ItemStack shield) {
        if (shield.isEnchanted()) {
            var enchantmentRegistry = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            int launchingLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ShieldsEnchantmentProvider.LAUNCHING), shield);
            if (launchingLevel > 0) {
                Handlers.handleLaunching(launchingLevel, player, shield);
            }

            int momentumLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ShieldsEnchantmentProvider.MOMENTUM), shield);
            if (momentumLevel > 0) {
                Handlers.handleMomentum(momentumLevel, player, shield);
            }
        }

        return InteractionResult.PASS;
    }

    public static class Handlers {
        public static void handleLifebound(int enchantmentLevel, Player player, LivingEntity attacker) {
            // 1/4 chance, chance increases by 5% with each level
            int chance = 25 + (5 * enchantmentLevel);
            if (player.level().random.nextInt(100) >= chance) {
                return;
            }

            // 0.75 heart at lvl 1
            // 1.25 hearts at lvl 2
            float damage = 0.75f + (0.5f * (enchantmentLevel - 1));
            player.heal(damage);

            var registryLookup = player.level().registryAccess();
            var magicDamageSouce = new DamageSource(registryLookup.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC));
            attacker.setLastHurtByPlayer(player);
            attacker.hurt(magicDamageSouce, damage);
        }

        public static void handleLaunching(int enchantmentLevel, Player player, ItemStack shield) {
            // 20% chance of launching to actually happen, regardless of level
            if (player.level().random.nextInt(100) >= 20) {
                return;
            }

            for (int i = 0; i < enchantmentLevel + 1; i++) {
                int radius = 2;

                if (enchantmentLevel == 1) {
                    radius = 4;
                } else {
                    radius = 2 + (enchantmentLevel - 1);
                }

                // Give all players around player resistance for 0.1s at 255 strength.
                player.level().getEntities(player, player.getBoundingBox().inflate(radius), entity -> entity instanceof Player).forEach(entity -> {
                    ((Player) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2, 255));
                    // Move them up into the air slightly as well so they get launched further.
                    entity.move(MoverType.SELF, new Vec3(0, 0.5, 0));
                });

                player.level().explode(player, player.getX(), player.getY() + 0.5f, player.getZ(), 2, Level.ExplosionInteraction.NONE);
            }

            // Damage the shield by 5 durability for each level
            shield.hurtAndBreak(5 * enchantmentLevel, player, player.getEquipmentSlotForItem(shield));
        }

        public static void handleEvokering(ServerLevel serverLevel, int enchantmentLevel, LivingEntity attacker, LivingEntity defender) {
            // 1/4 chance, chance increases by 5% with each level
            int chance = 25 + (5 * enchantmentLevel);
            if (serverLevel.random.nextInt(100) >= chance) {
                return;
            }

            EvokerFangs evokerFangs = new EvokerFangs(serverLevel, attacker.getX(), attacker.getY(), attacker.getZ(), 0, -6, defender);
            serverLevel.addFreshEntity(evokerFangs);
        }

        public static void handleMomentum(int momentumLevel, Player player, ItemStack shield) {
            // Give the player a speed III boost for 3 seconds. Each level adds 0.4s to the duration.
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 + (8 * momentumLevel), 3, true, false));

            // Damage the shield by 1 durability for each level
            shield.hurtAndBreak(momentumLevel, player, player.getEquipmentSlotForItem(shield));
        }
    }
}
