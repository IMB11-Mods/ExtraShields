package dev.imb11.shields.fabric;
//? fabric {

import dev.imb11.shields.Shields;
import dev.imb11.shields.enchantments.ShieldEnchantmentLootHelper;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

public class ShieldsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Shields.init();
        ShieldsItems.initialize();
        LootTableEvents.MODIFY.register(ShieldEnchantmentLootHelper::modifyLootTables);
    }
}
//?}