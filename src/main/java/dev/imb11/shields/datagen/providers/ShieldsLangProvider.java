//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.items.BannerShieldItemWrapper;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
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
    public ShieldsLangProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        try {
            Optional<Path> path = packOutput.getModContainer().findPath("assets/shields/lang/en_us.base.json");

            if (path.isPresent()) {
                translationBuilder.add(path.get());
            } else {
                throw new RuntimeException("The existing language file could not be found in assets!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<BannerShieldItemWrapper, String> shieldNameMap = ShieldsItems.SHIELD_ITEMS.stream()
                .collect(Collectors.toMap(
                        shield -> shield,
                        shield -> WordUtils.capitalize(shield.builtInRegistryHolder().key().identifier().getPath().replace("_", " "))
                ));

        for (Map.Entry<BannerShieldItemWrapper, String> bannerShieldItemWrapperStringEntry : shieldNameMap.entrySet()) {
            String shieldID = bannerShieldItemWrapperStringEntry.getKey().builtInRegistryHolder().key().identifier().getPath();
            for (DyeColor dyeColorStringEntry : DyeColor.values()) {
                String dyeID = dyeColorStringEntry.getName();
                translationBuilder.add("item.shields.%s.%s".formatted(shieldID, dyeID), WordUtils.capitalize(dyeID.replace("_", " ")) + " " + bannerShieldItemWrapperStringEntry.getValue());
            }
        }
    }
}
//?}