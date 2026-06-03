package dev.imb11.shields.client;

//? fabric {
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
//?}
import dev.imb11.shields.Shields;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.model.geom.ModelLayerLocation;

import java.util.ArrayList;
import java.util.List;

public class ShieldsClient {

    public static final List<ModelLayerLocation> LAYERS = new ArrayList<>();

    public static void registerDynamicShield(String id) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(Shields.of(id), "main");

        LAYERS.add(modelLayer);

        //? fabric {
        ModelLayerRegistry.registerModelLayer(modelLayer, ShieldModel::createLayer);
        //?}
    }
}
