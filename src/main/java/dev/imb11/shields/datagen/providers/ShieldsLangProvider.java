//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.items.BannerShieldItemWrapper;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import org.apache.commons.text.WordUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ShieldsLangProvider extends FabricLanguageProvider {
    public ShieldsLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        try {
            Optional<Path> path = dataOutput.getModContainer().findPath("assets/shields/lang/en_us.base.json");

            if (path.isPresent()) {
                translationBuilder.add(path.get());
            } else {
                throw new RuntimeException("The existing language file could not be found in assets!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Item> dyeItems = BuiltInRegistries.ITEM.stream().filter(item -> item instanceof DyeItem).toList();
        Map<DyeColor, String> dyeNameMap = dyeItems.stream()
                .map(item -> (DyeItem) item)
                .collect(Collectors.toMap(
                        DyeItem::getDyeColor,
                        dyeItem -> WordUtils.capitalize(dyeItem.getDyeColor().getName().replace("_", " ")),
                        (existing, replacement) -> existing
                ));
        Map<BannerShieldItemWrapper, String> shieldNameMap = ShieldsItems.SHIELD_ITEMS.stream()
                .collect(Collectors.toMap(
                        shield -> shield,
                        shield -> WordUtils.capitalize(shield.builtInRegistryHolder().key().location().getPath().replace("_", " "))
                ));

        for (Map.Entry<BannerShieldItemWrapper, String> bannerShieldItemWrapperStringEntry : shieldNameMap.entrySet()) {
            String shieldID = bannerShieldItemWrapperStringEntry.getKey().builtInRegistryHolder().key().location().getPath();
            for (Map.Entry<DyeColor, String> dyeColorStringEntry : dyeNameMap.entrySet()) {
                String dyeID = dyeColorStringEntry.getKey().getName();
                translationBuilder.add("item.shields." + shieldID + "." + dyeID, dyeColorStringEntry.getValue() + " " + bannerShieldItemWrapperStringEntry.getValue());
            }
        }
    }
}
//?}