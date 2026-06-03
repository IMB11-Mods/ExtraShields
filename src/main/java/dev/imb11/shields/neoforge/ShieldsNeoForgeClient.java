//? neoforge {
/*package dev.imb11.shields.neoforge;

import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ExtraShieldSpecialRenderer;
import dev.imb11.shields.client.ShieldsClient;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

@EventBusSubscriber(modid = Shields.MOD_ID, value = Dist.CLIENT)
public class ShieldsNeoForgeClient {

    @SubscribeEvent
    public static void registerSpecialModels(RegisterSpecialModelRendererEvent event) {
        event.register(Shields.of("shields"), ExtraShieldSpecialRenderer.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (ModelLayerLocation layer : ShieldsClient.LAYERS) {
            event.registerLayerDefinition(layer, ShieldModel::createLayer);
        }
    }
}
*///?}