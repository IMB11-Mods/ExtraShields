package dev.imb11.shields.client;

//? fabric {
import dev.imb11.shields.Shields;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
//?}
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShieldsClient {
    @ApiStatus.Internal
    public static LinkedHashMap<String, List<Identifier>> REGISTERED_MATERIALS = new LinkedHashMap<>();

    public static void registerDynamicShield(String id) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(Shields.of(id), "main");

        Identifier shieldBase = (Shields.of(id + "_base"));
        Identifier shieldBaseNoPattern = (Shields.of(id + "_base_nopattern"));

        REGISTERED_MATERIALS.put(id, List.of(shieldBase, shieldBaseNoPattern));

        //? fabric {
        ModelLayerRegistry.registerModelLayer(modelLayer, ShieldModel::createLayer);
        //?}
        SpecialModelRenderers.ID_MAPPER.put(Shields.of("shields"), ExtraShieldSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
