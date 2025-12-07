package dev.imb11.shields.fabric;
//? fabric {

/*import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import dev.imb11.shields.Shields;
import dev.imb11.shields.enchantments.ShieldEnchantmentLootHelper;
import dev.imb11.shields.enchantments.ShieldsEnchantmentEffects;
import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShieldsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Shields.init();
        ShieldsItems.initialize();
        LootTableEvents.MODIFY.register(ShieldEnchantmentLootHelper::modifyLootTables);
    }
}
*///?}