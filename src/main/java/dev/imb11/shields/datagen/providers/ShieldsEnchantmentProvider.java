//? fabric {
/*package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class ShieldsEnchantmentProvider extends FabricDynamicRegistryProvider {

    public ShieldsEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        var enchantmentsLookup = entries.getLookup(Registries.ENCHANTMENT);

        register(entries, ShieldsEnchantmentKeys.EVOKERING, new Enchantment.Builder(
                Enchantment.definition(
                        provider.lookupOrThrow(Registries.ITEM).getOrThrow(ConventionalItemTags.SHIELD_TOOLS),
                        // Weight (lowest rarity)
                        1,
                        // Max level
                        3,
                        // min cost for each level: 20 + (level - 1)*10
                        Enchantment.dynamicCost(20, 10),
                        // max cost for each level: 45 + (level - 1)*15
                        Enchantment.dynamicCost(45, 15),
                        5,
                        EquipmentSlotGroup.HAND
                )).exclusiveWith(enchantmentsLookup.getOrThrow(ShieldsEnchantmentTagProvider.EVOKERING_EXCLUSIVE_SET))
        );

        register(entries, ShieldsEnchantmentKeys.LAUNCHING, new Enchantment.Builder(
                Enchantment.definition(
                        provider.lookupOrThrow(Registries.ITEM).getOrThrow(ConventionalItemTags.SHIELD_TOOLS),
                        // Weight (less common)
                        5,
                        // Max level
                        3,
                        // min cost for each level: 10 + (level - 1)*5
                        Enchantment.dynamicCost(10, 5),
                        // max cost for each level: 25 + (level - 1)*10
                        Enchantment.dynamicCost(25, 10),
                        5,
                        EquipmentSlotGroup.HAND
                )).exclusiveWith(enchantmentsLookup.getOrThrow(ShieldsEnchantmentTagProvider.LAUNCHING_EXCLUSIVE_SET))
        );

        register(entries, ShieldsEnchantmentKeys.LIFEBOUND, new Enchantment.Builder(
                Enchantment.definition(
                        provider.lookupOrThrow(Registries.ITEM).getOrThrow(ConventionalItemTags.SHIELD_TOOLS),
                        // Weight
                        3,
                        // Max level
                        2,
                        // min cost for each level: 12 + (level - 1)*7
                        Enchantment.dynamicCost(12, 7),
                        // max cost for each level: 30 + (level - 1)*10
                        Enchantment.dynamicCost(30, 10),
                        5,
                        EquipmentSlotGroup.HAND
                )).exclusiveWith(enchantmentsLookup.getOrThrow(ShieldsEnchantmentTagProvider.LIFEBOUND_EXCLUSIVE_SET))
        );

        register(entries, ShieldsEnchantmentKeys.MOMENTUM, new Enchantment.Builder(
                Enchantment.definition(
                        provider.lookupOrThrow(Registries.ITEM).getOrThrow(ConventionalItemTags.SHIELD_TOOLS),
                        // Weight (common)
                        12,
                        // Max level
                        5,
                        // min cost for each level: 5 + (level - 1)*5
                        Enchantment.dynamicCost(5, 5),
                        // max cost for each level: 20 + (level - 1)*8
                        Enchantment.dynamicCost(20, 8),
                        5,
                        EquipmentSlotGroup.HAND
                )).exclusiveWith(enchantmentsLookup.getOrThrow(ShieldsEnchantmentTagProvider.MOMENTUM_EXCLUSIVE_SET))
        );

        register(entries, ShieldsEnchantmentKeys.BRACING, new Enchantment.Builder(
                Enchantment.definition(
                        provider.lookupOrThrow(Registries.ITEM).getOrThrow(ConventionalItemTags.SHIELD_TOOLS),
                        // Weight
                        10,
                        // Max level
                        5,
                        // min cost for each level: 8 + (level - 1)*6
                        Enchantment.dynamicCost(8, 6),
                        // max cost for each level: 25 + (level - 1)*10
                        Enchantment.dynamicCost(25, 10),
                        5,
                        EquipmentSlotGroup.HAND
                )).exclusiveWith(enchantmentsLookup.getOrThrow(ShieldsEnchantmentTagProvider.BRACING_EXCLUSIVE_SET))
        );
    }

    private void register(Entries entries, ResourceKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.location()), resourceConditions);
    }

    @Override
    public String getName() {
        return "ExtraShields Enchantment Provider";
    }
}
*///?}