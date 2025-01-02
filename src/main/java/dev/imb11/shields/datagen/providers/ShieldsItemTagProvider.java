package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ShieldsItemTagProvider extends FabricTagProvider.ItemTagProvider {
    private final TagKey<Item> ENCHANTABLE_DURABILITY = TagKey.create(Registries.ITEM, ResourceLocation.tryParse("enchantable/durability"));

    public ShieldsItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var shieldKeys = ShieldsItems.SHIELD_ITEMS.stream()
                .map(item -> ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(item)))
                .toList();

        this.tag(ENCHANTABLE_DURABILITY)
                .addAll(shieldKeys);

        this.tag(ConventionalItemTags.SHIELD_TOOLS)
                .addAll(shieldKeys);
    }
}
