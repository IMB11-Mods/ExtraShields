//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.items.BannerShieldItemWrapper;
import dev.imb11.shields.items.ShieldsItemTags;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ShieldsItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ShieldsItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ArrayList<Item> shieldKeys = new ArrayList<>(ShieldsItems.SHIELD_ITEMS);

        this.valueLookupBuilder(ShieldsItemTags.SHIELDS)
                .addAll(shieldKeys);

        this.valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .addOptionalTag(ShieldsItemTags.SHIELDS);

        this.valueLookupBuilder(ConventionalItemTags.SHIELD_TOOLS)
                .addOptionalTag(ShieldsItemTags.SHIELDS);
    }
}
//?}