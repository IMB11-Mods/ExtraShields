package dev.imb11.shields.fabric;
//? fabric {

import dev.imb11.shields.Shields;
import dev.imb11.shields.enchantments.ShieldEnchantmentLootHelper;
import dev.imb11.shields.enchantments.ShieldsEnchantmentEffects;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;

public class ShieldsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Shields.init();
        ShieldsItems.initialize();
        LootTableEvents.MODIFY.register(ShieldEnchantmentLootHelper::modifyLootTables);
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipFlag, tooltip) -> {
            if (itemStack.has(DataComponents.BLOCKS_ATTACKS)) {
                var blocks = itemStack.get(DataComponents.BLOCKS_ATTACKS);
                tooltip.add(Component.empty());
                tooltip.add(Component.translatable("shields.attribute.axe").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("shields.attribute.seconds", ShieldsEnchantmentEffects.getModifiedCooldown(tooltipContext.registries(), itemStack, blocks.disableCooldownScale()*5)).withStyle(ChatFormatting.DARK_GREEN));
            }
        });
    }
}
//?}