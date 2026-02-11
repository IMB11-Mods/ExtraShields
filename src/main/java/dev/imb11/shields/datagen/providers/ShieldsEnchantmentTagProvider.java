//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class ShieldsEnchantmentTagProvider extends FabricTagsProvider<Enchantment> {
    public static final TagKey<Enchantment> EVOKERING_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, Identifier.tryParse("shields:evokering_exclusive_set"));
    public static final TagKey<Enchantment> LAUNCHING_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, Identifier.tryParse("shields:launching_exclusive_set"));
    public static final TagKey<Enchantment> LIFEBOUND_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, Identifier.tryParse("shields:lifebound_exclusive_set"));
    public static final TagKey<Enchantment> MOMENTUM_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, Identifier.tryParse("shields:momentum_exclusive_set"));
    public static final TagKey<Enchantment> BRACING_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, Identifier.tryParse("shields:bracing_exclusive_set"));

    public ShieldsEnchantmentTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateRawBuilder(EVOKERING_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.identifier());

        this.getOrCreateRawBuilder(LAUNCHING_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.identifier());

        this.getOrCreateRawBuilder(LIFEBOUND_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.identifier());

        this.getOrCreateRawBuilder(MOMENTUM_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.BRACING.identifier());

        this.getOrCreateRawBuilder(BRACING_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.identifier());

        this.getOrCreateRawBuilder(EnchantmentTags.IN_ENCHANTING_TABLE)
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.BRACING.identifier());

        this.getOrCreateRawBuilder(EnchantmentTags.TREASURE)
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.identifier());

        this.getOrCreateRawBuilder(EnchantmentTags.NON_TREASURE)
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.identifier())
                .addOptionalElement(ShieldsEnchantmentKeys.BRACING.identifier());
    }
}
//?}