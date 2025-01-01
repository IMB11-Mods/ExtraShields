package dev.imb11.shields.datagen;

import com.mojang.serialization.Codec;
import dev.imb11.shields.client.ShieldsClient;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ShieldsAtlasProvider extends FabricCodecDataProvider<ShieldsAtlas> {
    protected ShieldsAtlasProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "atlases", ShieldsAtlas.CODEC);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, ShieldsAtlas> biConsumer, HolderLookup.Provider provider) {
        var shieldsAtlas = new ShieldsAtlas(ShieldsClient.REGISTERED_MATERIALS.stream().map(material -> new ShieldsAtlas.Source("single", material.texture().toString())).toList());
        biConsumer.accept(ResourceLocation.tryParse("blocks"), shieldsAtlas);
    }

    @Override
    public String getName() {
        return "Shields Atlas";
    }
}
