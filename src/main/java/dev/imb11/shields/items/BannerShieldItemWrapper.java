package dev.imb11.shields.items;

import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

public class BannerShieldItemWrapper extends ShieldLibItem {

    public BannerShieldItemWrapper(Properties settings, int coolDownTicks, int enchantability, Item... repairItems) {
        super(settings, coolDownTicks, enchantability, repairItems);
    }

    public BannerShieldItemWrapper(Properties settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        super(settings, coolDownTicks, enchantability, repairItemTag);
    }
}
