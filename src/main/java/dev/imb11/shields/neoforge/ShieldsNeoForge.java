//? neoforge {
/*package dev.imb11.shields.neoforge;

import dev.imb11.shields.Shields;
import dev.imb11.shields.enchantments.ShieldEnchantmentLootHelper;
import dev.imb11.shields.items.ShieldCollection;
import dev.imb11.shields.items.ShieldsItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;

@Mod(Shields.MOD_ID)
@EventBusSubscriber(modid = Shields.MOD_ID)
public class ShieldsNeoForge {
    public ShieldsNeoForge(IEventBus bus) {
        Shields.init();
    }

    @SubscribeEvent
    public static void registerItems(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            ShieldsItems.initialize();
        }
    }

    @SubscribeEvent
	public static void addTooltip(ItemTooltipEvent event) {
        Shields.addTooltip(event.getItemStack(), event.getContext(), event.getToolTip());
    }

    @SubscribeEvent
    public static void shieldAnvilRecipes(AnvilUpdateEvent event) {

        ItemStack expectedInput1 = event.getLeft();
        ItemStack expectedPlating = event.getRight();

        for (ShieldCollection itemEntry : ShieldsItems.SHIELD_COLLECTIONS.values()) {
            var plating = itemEntry.plating();
            var input1 = itemEntry.shieldItem();
            var output = itemEntry.platedShieldItem();

            // Check if the input items are the same as the expected items
            if (expectedInput1.getItem() == input1 && expectedPlating.getItem() == plating) {
                event.setOutput(expectedInput1.transmuteCopy(output));
                event.setXpCost(1);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void loadLootModifiers(LootTableLoadEvent event) {
        ShieldEnchantmentLootHelper.modifyLootTables(event.getKey(), event.getTable(), event.getRegistries());
    }
}
*///?}