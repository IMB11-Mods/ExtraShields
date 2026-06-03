//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ExtraShieldSpecialRenderer;
import dev.imb11.shields.client.ShieldsClient;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShieldsModelProvider extends FabricModelProvider {
	public ShieldsModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		Identifier vanillaShieldModelLocation = ModelLocationUtils.getModelLocation(Items.SHIELD);

		for (Map.Entry<String, List<Identifier>> materials : ShieldsClient.REGISTERED_MATERIALS.entrySet()) {
			var modelLocation = Shields.of("item/" + materials.getKey());
			var modelLayer = Shields.of(materials.getKey());

			// Item models
			ModelTemplate shieldTemplate = new ModelTemplate(Optional.of(vanillaShieldModelLocation), Optional.empty(), TextureSlot.PARTICLE);
			var item = ShieldsItems.SHIELD_ITEMS_BY_ID.get(materials.getKey());
			Item sprucePlanks = getParticleIcon(item);
			shieldTemplate.create(modelLocation, TextureMapping.singleSlot(TextureSlot.PARTICLE, new Material(ModelLocationUtils.getModelLocation(sprucePlanks))), itemModelGenerator.modelOutput);

			ModelTemplate blockingShieldTemplate = new ModelTemplate(Optional.of(vanillaShieldModelLocation.withSuffix("_blocking")), Optional.empty(), TextureSlot.PARTICLE);
			blockingShieldTemplate.create(modelLocation.withSuffix("_blocking"), TextureMapping.singleSlot(TextureSlot.PARTICLE, new Material(ModelLocationUtils.getModelLocation(sprucePlanks))), itemModelGenerator.modelOutput);

			// Client Items for Shields

			var model = new ExtraShieldSpecialRenderer.Unbaked(modelLayer, materials.getValue().getFirst(), materials.getValue().getLast());
			ItemModel.Unbaked normal = ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(item), model);
			ItemModel.Unbaked blocking = ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(item, "_blocking"), model);
			itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(ExtraShieldSpecialRenderer.DEFAULT_TRANSFORMATION, ItemModelUtils.isUsingItem(), blocking, normal));
		}

		// Platings
		for (Item shieldPlatingItem : ShieldsItems.SHIELD_PLATING_ITEMS) {
			itemModelGenerator.generateFlatItem(shieldPlatingItem, ModelTemplates.FLAT_ITEM);
		}

		// Repair kit
		itemModelGenerator.generateFlatItem(ShieldsItems.SHIELD_REPAIR_KIT, ModelTemplates.FLAT_ITEM);

	}

	private static @NonNull Item getParticleIcon(ShieldItem item) {
		for (Map.Entry<Item, Item[]> entry : ShieldsItems.PLATING_UPGRADE_MAP.entrySet()) {
			if (Arrays.stream(entry.getValue()).toList().contains(item)) return entry.getKey();
		}
		return Blocks.SPRUCE_PLANKS.asItem(); //shouldn't ever trigger but just in case
	}
}
//?}