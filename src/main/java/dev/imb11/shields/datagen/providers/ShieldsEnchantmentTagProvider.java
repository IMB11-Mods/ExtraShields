package dev.imb11.shields.datagen.providers;

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
        this.tag(EVOKERING_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.LAUNCHING.location())
                .addOptional(ShieldsEnchantmentProvider.LIFEBOUND.location())
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location());

        this.tag(LAUNCHING_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.EVOKERING.location())
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location())
                .addOptional(ShieldsEnchantmentProvider.LIFEBOUND.location());

        this.tag(LIFEBOUND_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.EVOKERING.location())
                .addOptional(ShieldsEnchantmentProvider.LAUNCHING.location())
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location());

        this.tag(MOMENTUM_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.LIFEBOUND.location())
                .addOptional(ShieldsEnchantmentProvider.LAUNCHING.location())
                .addOptional(ShieldsEnchantmentProvider.EVOKERING.location())
                .addOptional(ShieldsEnchantmentProvider.BRACING.location());

        this.tag(BRACING_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location());

        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location())
                .addOptional(ShieldsEnchantmentProvider.BRACING.location());

        this.tag(EnchantmentTags.TREASURE)
                .addOptional(ShieldsEnchantmentProvider.EVOKERING.location())
                .addOptional(ShieldsEnchantmentProvider.LAUNCHING.location())
                .addOptional(ShieldsEnchantmentProvider.LIFEBOUND.location());

        this.tag(EnchantmentTags.NON_TREASURE)
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location())
                .addOptional(ShieldsEnchantmentProvider.BRACING.location());
    }
}
