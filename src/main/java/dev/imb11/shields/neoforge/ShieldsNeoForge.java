//? neoforge {
/*package dev.imb11.shields.neoforge;

import dev.imb11.shields.Shields;
import dev.imb11.shields.compat.eiv.EivCompat;
import dev.imb11.shields.enchantments.ShieldEnchantmentLootHelper;
import dev.imb11.shields.items.ShieldsItems;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;

@Mod(Shields.MOD_ID)
@EventBusSubscriber(modid = Shields.MOD_ID)
public class ShieldsNeoForge {
    public ShieldsNeoForge(IEventBus bus) {
        Shields.init();
    }

    @SubscribeEvent
    public static void registerItems(RegisterEvent registerEvent) {
        if (registerEvent.getRegistryKey().equals(Registries.ITEM)) {
            ShieldsItems.initialize();
        }
    }

    @SubscribeEvent
    public static void registerItems(FMLCommonSetupEvent registerEvent) {
        if (ModList.get().isLoaded("eiv")) {
            EivCompat.init();
        }
    }

    @SubscribeEvent
    public static void shieldAnvilRecipes(AnvilUpdateEvent anvilUpdateEvent) {
        // [plating (input2)], [input1, output]
        var recipeMap = ShieldsItems.PLATING_UPGRADE_MAP;

        ItemStack expectedInput1 = anvilUpdateEvent.getLeft();
        ItemStack expectedPlating = anvilUpdateEvent.getRight();

        for (Map.Entry<Item, Item[]> itemEntry : recipeMap.entrySet()) {
            var plating = itemEntry.getKey();
            var input1 = itemEntry.getValue()[0];
            var output = itemEntry.getValue()[1];

            // Check if the input items are the same as the expected items
            if (expectedInput1.getItem() == input1 && expectedPlating.getItem() == plating) {
                anvilUpdateEvent.setOutput(expectedInput1.transmuteCopy(output));
                anvilUpdateEvent.setXpCost(1);
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