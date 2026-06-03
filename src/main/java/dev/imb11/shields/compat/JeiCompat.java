package dev.imb11.shields.compat;

import dev.imb11.shields.Shields;
import dev.imb11.shields.items.ShieldCollection;
import dev.imb11.shields.items.ShieldsItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.library.plugins.vanilla.anvil.AnvilRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@JeiPlugin
public class JeiCompat implements IModPlugin {
	@Override
	public Identifier getPluginUid() {
		return Identifier.fromNamespaceAndPath(Shields.MOD_ID, "jei");
	}

	@Override
	public void registerRecipes(IRecipeRegistration registry) {
		ArrayList<IJeiAnvilRecipe> recipes = new ArrayList<>();
		for (ShieldCollection itemEntry : ShieldsItems.SHIELD_COLLECTIONS.values()) {
			Item shield = itemEntry.shieldItem();
			Item plating = itemEntry.plating();
			Item platedShield = itemEntry.platedShieldItem();
			recipes.add(new AnvilRecipe(
					Collections.singletonList(shield.getDefaultInstance()),
					Collections.singletonList(plating.getDefaultInstance()),
					Collections.singletonList(platedShield.getDefaultInstance()),
					BuiltInRegistries.ITEM.getKey(platedShield)
			));

		}
		registry.addRecipes(RecipeTypes.ANVIL, recipes);
	}
}
