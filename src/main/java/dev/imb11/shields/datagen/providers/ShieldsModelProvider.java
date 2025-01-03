package dev.imb11.shields.datagen.providers;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import com.google.gson.Gson;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShieldsModelProvider extends FabricModelProvider {
    private static final TextureSlot SHIELD_TEXTURE_SLOT = TextureSlot.create("shield");

    private static final ModelTemplate SHIELD_BLOCKING_MODEL_TEMPLATE = new ModelTemplate(
            Optional.of(ResourceLocation.tryParse("fabricshieldlib:item/fabric_banner_shield_blocking")),
            Optional.empty(),
            SHIELD_TEXTURE_SLOT
    );

    public ShieldsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        // No block state models needed for shields
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        Gson gson = new Gson();
        for (FabricBannerShieldItem shieldItem : ShieldsItems.SHIELD_ITEMS) {
            String shieldName = BuiltInRegistries.ITEM.getKey(shieldItem).getPath();

            TextureMapping textureMapping = new TextureMapping();
            textureMapping.put(SHIELD_TEXTURE_SLOT, ResourceLocation.tryParse("shields:item/" + shieldName));

            SHIELD_BLOCKING_MODEL_TEMPLATE.create(
                    ModelLocationUtils.getModelLocation(shieldItem, "_blocking"),
                    textureMapping,
                    itemModelGenerators.output
            );

            itemModelGenerators.output.accept(
                    ModelLocationUtils.getModelLocation(shieldItem),
                    () -> gson.toJsonTree(createShieldModelJson(shieldName))
            );
        }

        for (Item shieldPlatingItem : ShieldsItems.SHIELD_PLATING_ITEMS) {
            itemModelGenerators.generateFlatItem(shieldPlatingItem, ModelTemplates.FLAT_ITEM);
        }
    }

    private Map<String, Object> createShieldModelJson(String shieldName) {
        return Map.of(
                "parent", "fabricshieldlib:item/fabric_banner_shield",
                "textures", Map.of("shield", "shields:item/" + shieldName),
                "overrides", List.of(
                        Map.of(
                                "predicate", Map.of("blocking", 1),
                                "model", "shields:item/" + shieldName + "_blocking"
                        )
                )
        );
    }
}