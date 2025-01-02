package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.client.ShieldsClient;
import dev.imb11.shields.datagen.data.ShieldsAtlas;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ShieldsAtlasProvider extends FabricCodecDataProvider<ShieldsAtlas> {
    public ShieldsAtlasProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "atlases", ShieldsAtlas.CODEC);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, ShieldsAtlas> biConsumer, HolderLookup.Provider provider) {
        var shieldsAtlas = new ShieldsAtlas(ShieldsClient.REGISTERED_MATERIALS.stream().map(material -> new ShieldsAtlas.Source("single", material.texture().toString())).toList());
        biConsumer.accept(ResourceLocation.tryParse("blocks"), shieldsAtlas);
    }

    @Override
    public String getName() {
        return "Extra Shields Atlas";
    }
}
