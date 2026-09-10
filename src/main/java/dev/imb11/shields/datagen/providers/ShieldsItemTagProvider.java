//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.items.ShieldsItemTags;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ShieldsItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public ShieldsItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ArrayList<ResourceKey<Item>> shieldKeys = new ArrayList<>(ShieldsItems.shieldItems(false).stream().map(p->p.builtInRegistryHolder().key()).toList());

        this.builder(ShieldsItemTags.SHIELDS)
                .addAll(shieldKeys);

        this.builder(ItemTags.DURABILITY_ENCHANTABLE)
                .addOptionalTag(ShieldsItemTags.SHIELDS);

        this.builder(ConventionalItemTags.SHIELD_TOOLS)
                .addOptionalTag(ShieldsItemTags.SHIELDS);
    }
}
//?}