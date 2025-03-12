package dev.imb11.shields.enchantments;

import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;

public class ShieldEnchantmentLootHelper {
    private static final ResourceLocation CHESTS_NETHER_BRIDGE = ResourceLocation.parse("chests/nether_bridge");
    private static final ResourceLocation CHESTS_WOODLAND_MANSION = ResourceLocation.parse("chests/woodland_mansion");
    private static final ResourceLocation CHESTS_OMINOUS_VAULT_RARE = ResourceLocation.parse("chests/trial_chambers/reward_ominous_rare");

    public static void modifyLootTables(ResourceKey<LootTable> lootTableResourceKey, LootTable.Builder builder, LootTableSource lootTableSource, HolderLookup.Provider provider) {
        var enchantmentRegistryLookup = provider.lookupOrThrow(Registries.ENCHANTMENT);

        if (lootTableSource.isBuiltin() && CHESTS_WOODLAND_MANSION.equals(lootTableResourceKey.location())) {
            builder.modifyPools(poolBuilder -> {
              poolBuilder.add(LootItem.lootTableItem(Items.BOOK)
                      .setWeight(6)
                      .apply(new EnchantRandomlyFunction.Builder().withEnchantment(
                              enchantmentRegistryLookup.getOrThrow(ShieldsEnchantmentKeys.EVOKERING)
                      )));
            });
        }

        if (lootTableSource.isBuiltin() && CHESTS_NETHER_BRIDGE.equals(lootTableResourceKey.location())) {
            builder.modifyPools(poolBuilder -> {
              poolBuilder.add(LootItem.lootTableItem(Items.BOOK)
                      .setWeight(5)
                      .apply(new EnchantRandomlyFunction.Builder().withEnchantment(
                              enchantmentRegistryLookup.getOrThrow(ShieldsEnchantmentKeys.LIFEBOUND)
                      )));
            });
        }

        if (lootTableSource.isBuiltin() && CHESTS_OMINOUS_VAULT_RARE.equals(lootTableResourceKey.location())) {
            builder.modifyPools(poolBuilder -> {
              poolBuilder.add(LootItem.lootTableItem(Items.BOOK)
                      .setWeight(3)
                      .apply(new EnchantRandomlyFunction.Builder().withEnchantment(
                              enchantmentRegistryLookup.getOrThrow(ShieldsEnchantmentKeys.LAUNCHING)
                      )));
            });
        }
    }
}
