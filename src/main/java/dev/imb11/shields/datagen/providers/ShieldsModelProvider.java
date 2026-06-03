//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ExtraShieldSpecialRenderer;
import dev.imb11.shields.items.ShieldCollection;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;

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


		for (Map.Entry<String, ShieldCollection> materials : ShieldsItems.SHIELD_COLLECTIONS.entrySet()) {
			var collection = materials.getValue();

			// Item models
			if (collection.shieldItemKey().identifier().getNamespace().equals(Shields.MOD_ID)) {
				generateShield(itemModelGenerator, collection.shieldItemKey().identifier().getPath(), collection, collection.shieldItem(), collection.shieldBaseTexture(), collection.shieldBaseNoPatternTexture());
			}
			generateShield(itemModelGenerator, collection.platedShieldItemKey().identifier().getPath(), collection, collection.platedShieldItem(), collection.platedShieldBaseTexture(), collection.platedShieldBaseNoPatternTexture());

			// Platings
			itemModelGenerator.generateFlatItem(collection.plating(), ModelTemplates.FLAT_ITEM);
		};

		// Repair kit
		itemModelGenerator.generateFlatItem(ShieldsItems.SHIELD_REPAIR_KIT, ModelTemplates.FLAT_ITEM);

	}

	private static void generateShield(ItemModelGenerators itemModelGenerator, String id, ShieldCollection collection, ShieldItem item, Identifier texture, Identifier noPatternTexture) {
		Identifier vanillaShieldModelLocation = ModelLocationUtils.getModelLocation(Items.SHIELD);
		var modelLocation = Shields.of("item/" + id);
		var modelLayer = Shields.of(id);

		// Normal model
		ModelTemplate shieldTemplate = new ModelTemplate(Optional.of(vanillaShieldModelLocation), Optional.empty(), TextureSlot.PARTICLE);
		shieldTemplate.create(modelLocation, TextureMapping.singleSlot(TextureSlot.PARTICLE, new Material(ModelLocationUtils.getModelLocation(collection.plating()))), itemModelGenerator.modelOutput);

		// Blocking model
		ModelTemplate blockingShieldTemplate = new ModelTemplate(Optional.of(vanillaShieldModelLocation.withSuffix("_blocking")), Optional.empty(), TextureSlot.PARTICLE);
		blockingShieldTemplate.create(modelLocation.withSuffix("_blocking"), TextureMapping.singleSlot(TextureSlot.PARTICLE, new Material(ModelLocationUtils.getModelLocation(collection.plating()))), itemModelGenerator.modelOutput);

		// Client Items for Shields
		var model = new ExtraShieldSpecialRenderer.Unbaked(modelLayer, texture, noPatternTexture);
		ItemModel.Unbaked normal = ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(item), model);
		ItemModel.Unbaked blocking = ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(item, "_blocking"), model);
		itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(ExtraShieldSpecialRenderer.DEFAULT_TRANSFORMATION, ItemModelUtils.isUsingItem(), blocking, normal));
	}

}
//?}