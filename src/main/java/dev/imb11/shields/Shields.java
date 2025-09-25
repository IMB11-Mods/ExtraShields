package dev.imb11.shields;


import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import dev.imb11.shields.enchantments.ShieldEnchantmentLootHelper;
import dev.imb11.shields.enchantments.ShieldsEnchantmentEffects;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shields implements ModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger("Shields");

    @Override
    public void onInitialize() {
        ShieldsItems.initialize();

        ShieldEvents.BLOCK.register(ShieldsEnchantmentEffects::eventShieldBlock);
        ShieldEvents.DISABLE.register(ShieldsEnchantmentEffects::eventShieldDisabled);

        LootTableEvents.MODIFY.register(ShieldEnchantmentLootHelper::modifyLootTables);
    }
}
