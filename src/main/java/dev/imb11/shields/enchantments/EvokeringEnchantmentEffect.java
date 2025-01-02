package dev.imb11.shields.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.EvokerFangs;

public class EvokeringEnchantmentEffect {
    public static void handle(ServerLevel serverLevel, int enchantmentLevel, LivingEntity attacker, LivingEntity defender) {
        // 1/4 chance, chance increases by 5% with each level
        int chance = 25 + (5 * enchantmentLevel);
        if (serverLevel.random.nextInt(100) >= chance) {
            return;
        }

        EvokerFangs evokerFangs = new EvokerFangs(serverLevel, attacker.getX(), attacker.getY(), attacker.getZ(), 0, 0, defender);
        serverLevel.addFreshEntity(evokerFangs);
    }
}
