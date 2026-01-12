package dev.imb11.shields.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import dev.imb11.shields.items.ShieldsItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.Map;

public class RrvCompat implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
      init();
    }

    public static void init() {
        ItemView.addServerRecipeProvider(recipeList-> {
            for (Map.Entry<Item, Item[]> itemEntry : ShieldsItems.PLATING_UPGRADE_MAP.entrySet()) {
                Item shield = itemEntry.getValue()[0];
                Item plating = itemEntry.getKey();
                Item platedShield = itemEntry.getValue()[1];
                recipeList.add(new AnvilServerRecipe(new ItemStack(shield), new ItemStack(plating), new ItemStack(platedShield)));
            }
        });
        ItemView.addClientRecipeWrapper(AnvilServerRecipe.TYPE, modRecipe -> {
            return Collections.singletonList(new AnvilViewRecipe(modRecipe));
        });
    }
}
