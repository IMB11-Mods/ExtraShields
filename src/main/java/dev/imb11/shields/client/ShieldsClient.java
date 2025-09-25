package dev.imb11.shields.client;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShieldsClient implements ClientModInitializer {
    @ApiStatus.Internal
    public static ArrayList<Material> REGISTERED_MATERIALS = new ArrayList<>();

    public static void registerDynamicShield(String id, ShieldLibItem shieldItem) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(ResourceLocation.tryBuild("shields", id), "main");

        AtomicReference<ShieldModel> modelShield = new AtomicReference<>();

        Material shieldBase = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.tryBuild("shields", "entity/" + id + "_base"));
        Material shieldBaseNoPattern = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.tryBuild("shields", "entity/" + id + "_base_nopattern"));

        REGISTERED_MATERIALS.addAll(List.of(shieldBase, shieldBaseNoPattern));

        EntityModelLayerRegistry.registerModelLayer(modelLayer, ShieldModel::createLayer);
    }

    @Override
    public void onInitializeClient() {
    }
}
