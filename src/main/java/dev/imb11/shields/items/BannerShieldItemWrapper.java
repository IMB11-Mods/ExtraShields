package dev.imb11.shields.items;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import dev.imb11.shields.datagen.providers.ShieldsEnchantmentProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

public class BannerShieldItemWrapper extends FabricBannerShieldItem {
    public @Nullable ItemStack MIXIN$ITEM_STACK_VALUE = null;
    public @Nullable HolderLookup.RegistryLookup<Enchantment> ENCHANTMENT_LOOKUP = null;

    public BannerShieldItemWrapper(Properties settings, int coolDownTicks, int enchantability, Item... repairItems) {
        super(settings, coolDownTicks, enchantability, repairItems);
    }

    public BannerShieldItemWrapper(Properties settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        super(settings, coolDownTicks, enchantability, repairItemTag);
    }

    @Override
    public int getCoolDownTicks() {
        // Check for bracing enchantment, each level decreases cooldown ticks by 10%.
        if (MIXIN$ITEM_STACK_VALUE != null && ENCHANTMENT_LOOKUP != null) {
            var enchantment = ENCHANTMENT_LOOKUP.getOrThrow(ShieldsEnchantmentProvider.BRACING);
            int enchantmentLevel = MIXIN$ITEM_STACK_VALUE.getEnchantments().getLevel(enchantment);

            if (enchantmentLevel > 0) {
                MIXIN$ITEM_STACK_VALUE = null;
                ENCHANTMENT_LOOKUP = null;

                return (int) (super.getCoolDownTicks() * (1 - (0.1 * enchantmentLevel)));
            }

            MIXIN$ITEM_STACK_VALUE = null;
            ENCHANTMENT_LOOKUP = null;
        }

        return super.getCoolDownTicks();
    }
}
