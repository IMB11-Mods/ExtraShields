package dev.imb11.shields.items;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;

public record ShieldCollection(ShieldItem shieldItem, ShieldItem platedShieldItem, Item plating,
                               ResourceKey<Item> shieldItemKey, ResourceKey<Item> platedShieldItemKey, ResourceKey<Item> shieldPlatingKey,
                               Identifier shieldBaseTexture, Identifier shieldBaseNoPatternTexture,
                               Identifier platedShieldBaseTexture, Identifier platedShieldBaseNoPatternTexture) {
}
