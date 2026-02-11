package dev.imb11.shields.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ShieldsItemTags {
    public static TagKey<Item> SHIELDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("shields", "shields"));
    public static TagKey<Item> CONVENTIONAL_SHIELDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shield"));

}
