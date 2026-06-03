package dev.imb11.shields.client;

//? fabric {
import dev.imb11.shields.Shields;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
//?}
import dev.imb11.shields.Shields;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.LinkedHashMap;
import java.util.List;

public class ShieldsClient {

    public static void registerDynamicShield(String id) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(Shields.of(id), "main");

        //? fabric {
        ModelLayerRegistry.registerModelLayer(modelLayer, ShieldModel::createLayer);
        //?}
        SpecialModelRenderers.ID_MAPPER.put(Shields.of("shields"), ExtraShieldSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
