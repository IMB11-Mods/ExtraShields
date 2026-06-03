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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShieldsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Shields.init();
        ShieldsItems.initialize();
        LootTableEvents.MODIFY.register(ShieldEnchantmentLootHelper::modifyLootTables);
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipFlag, tooltip) -> {
            Shields.addTooltip(itemStack, tooltipContext, tooltip);
        });
    }
}
//?}