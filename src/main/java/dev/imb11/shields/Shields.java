package dev.imb11.shields;


import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import com.github.stellarwind22.shieldlib.lib.registry.ShieldCooldownModifier;
import dev.imb11.shields.enchantments.ShieldsEnchantmentEffects;
import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import dev.imb11.shields.items.ShieldsItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shields {
    public static final Logger LOGGER = LoggerFactory.getLogger("Shields");
    public static final String MOD_ID = "shields";

    public static void init() {

       ShieldLib.registerCooldownModifier((player, stack, blocksAttacks, original) -> {
           if (player != null) {
               var enchantmentLookup = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
               // Check for bracing enchantment, each level decreases cooldown ticks by 10%.
               if (stack != null) {
                   var enchantment = enchantmentLookup.getOrThrow(ShieldsEnchantmentKeys.BRACING);
                   int enchantmentLevel = stack.getEnchantments().getLevel(enchantment);

                   if (enchantmentLevel > 0) {
                       return (int) (original * (1 - (0.1 * enchantmentLevel)));
                   }
               }
           }


           return original;
       });
       ShieldEvents.BLOCK.register(ShieldsEnchantmentEffects::eventShieldBlock);
       ShieldEvents.DISABLE.register(ShieldsEnchantmentEffects::eventShieldDisabled);


   }
}
