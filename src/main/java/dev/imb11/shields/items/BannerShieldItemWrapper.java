package dev.imb11.shields.items;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;

public class BannerShieldItemWrapper extends ShieldItem {

    public BannerShieldItemWrapper(Properties settings, int coolDownTicks, int enchantability, Item... repairItems) {
        super(settings);
    }

    public BannerShieldItemWrapper(Properties settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        super(settings);
    }
}
