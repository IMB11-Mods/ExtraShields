package dev.imb11.shields;

import dev.imb11.shields.enchantments.ShieldsEnchantmentEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class Shields {
    public static final Logger LOGGER = LoggerFactory.getLogger("Shields");
    public static final String MOD_ID = "shields";

    public static Identifier of(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void addTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip) {
        HolderLookup.Provider registries = tooltipContext.registries();
        if (itemStack.has(DataComponents.BLOCKS_ATTACKS) && registries != null) {
            var blocks = Objects.requireNonNull(itemStack.get(DataComponents.BLOCKS_ATTACKS));
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("shields.attribute.axe").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("shields.attribute.seconds", ShieldsEnchantmentEffects.getModifiedCooldown(registries, itemStack, blocks.disableCooldownScale()*5)).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    public static void init() {

   }
}
