package dev.imb11.shields.client;

//? fabric {
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
//?}
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShieldsClient {
    @ApiStatus.Internal
    public static LinkedHashMap<String, List<Material>> REGISTERED_MATERIALS = new LinkedHashMap<>();

    public static void registerDynamicShield(String id) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(Identifier.fromNamespaceAndPath("shields", id), "main");

        AtomicReference<ShieldModel> modelShield = new AtomicReference<>();

        Material shieldBase = new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.fromNamespaceAndPath("shields", "entity/" + id + "_base"));
        Material shieldBaseNoPattern = new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.fromNamespaceAndPath("shields", "entity/" + id + "_base_nopattern"));

        REGISTERED_MATERIALS.put(id, List.of(shieldBase, shieldBaseNoPattern));

        //? fabric {
        ModelLayerRegistry.registerModelLayer(modelLayer, ShieldModel::createLayer);
        //?}
    }
}
