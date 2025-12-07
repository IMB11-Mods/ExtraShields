//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class ShieldsEnchantmentTagProvider extends FabricTagProvider<Enchantment> {
    public static final TagKey<Enchantment> EVOKERING_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.tryParse("shields:evokering_exclusive_set"));
    public static final TagKey<Enchantment> LAUNCHING_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.tryParse("shields:launching_exclusive_set"));
    public static final TagKey<Enchantment> LIFEBOUND_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.tryParse("shields:lifebound_exclusive_set"));
    public static final TagKey<Enchantment> MOMENTUM_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.tryParse("shields:momentum_exclusive_set"));
    public static final TagKey<Enchantment> BRACING_EXCLUSIVE_SET = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.tryParse("shields:bracing_exclusive_set"));

    public ShieldsEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateRawBuilder(EVOKERING_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.location())
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.location());

        this.getOrCreateRawBuilder(LAUNCHING_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.location())
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.location());

        this.getOrCreateRawBuilder(LIFEBOUND_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.location());

        this.getOrCreateRawBuilder(MOMENTUM_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.location())
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.BRACING.location());

        this.getOrCreateRawBuilder(BRACING_EXCLUSIVE_SET)
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.location());

        this.getOrCreateRawBuilder(EnchantmentTags.IN_ENCHANTING_TABLE)
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.location())
                .addOptionalElement(ShieldsEnchantmentKeys.BRACING.location());

        this.getOrCreateRawBuilder(EnchantmentTags.TREASURE)
                .addOptionalElement(ShieldsEnchantmentKeys.EVOKERING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.LAUNCHING.location())
                .addOptionalElement(ShieldsEnchantmentKeys.LIFEBOUND.location());

        this.getOrCreateRawBuilder(EnchantmentTags.NON_TREASURE)
                .addOptionalElement(ShieldsEnchantmentKeys.MOMENTUM.location())
                .addOptionalElement(ShieldsEnchantmentKeys.BRACING.location());
    }
}
//?}