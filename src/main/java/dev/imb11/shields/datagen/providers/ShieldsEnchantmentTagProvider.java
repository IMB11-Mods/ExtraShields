package dev.imb11.shields.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
        // Evokering is not allowed with launching or lifebound.
        this.tag(EVOKERING_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.LAUNCHING.location())
                .addOptional(ShieldsEnchantmentProvider.LIFEBOUND.location());

        // Launching is not allowed with evokering.
        this.tag(LAUNCHING_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.EVOKERING.location());

        // Lifebound is not allowed with evokering or momentum.
        this.tag(LIFEBOUND_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.EVOKERING.location())
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location());

        // Momentum is not allowed with lifebound or bracing.
        this.tag(MOMENTUM_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.LIFEBOUND.location())
                .addOptional(ShieldsEnchantmentProvider.BRACING.location());

        // Bracing is not allowed with momentum.
        this.tag(BRACING_EXCLUSIVE_SET)
                .addOptional(ShieldsEnchantmentProvider.MOMENTUM.location());
    }
}
