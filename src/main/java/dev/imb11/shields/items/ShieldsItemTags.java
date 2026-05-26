package dev.imb11.shields.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ShieldsItemTags {
    /// Shields from this mod.
    public static TagKey<Item> SHIELDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("shields", "shields"));
    /// Shields from any mod.
    public static TagKey<Item> CONVENTIONAL_SHIELDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shield"));

}
