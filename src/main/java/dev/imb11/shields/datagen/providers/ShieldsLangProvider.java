//? fabric {
package dev.imb11.shields.datagen.providers;

import dev.imb11.shields.Shields;
import dev.imb11.shields.items.ShieldsItemTags;
import dev.imb11.shields.items.ShieldsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import org.apache.commons.text.WordUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

        ShieldsItems.SHIELD_COLLECTIONS.forEach((s, collection) -> {
            addTranslations(translationBuilder, collection.shieldItemKey());
            addTranslations(translationBuilder, collection.platedShieldItemKey());
        });

        translationBuilder.add(ShieldsItemTags.SHIELDS, "Shields");
    }

	private void addTranslations(TranslationBuilder translationBuilder, ResourceKey<Item> itemResourceKey) {
        if (itemResourceKey.identifier().getNamespace().equals(Shields.MOD_ID)) {
            String shieldID = itemResourceKey.identifier().getPath();
            for (DyeColor dyeColorStringEntry : DyeColor.values()) {
                String dyeID = dyeColorStringEntry.getName();
                translationBuilder.add("item.shields.%s.%s".formatted(shieldID, dyeID), toText(dyeID) + " " + toText(shieldID));
            }
        }
	}

    private static String toText(String shieldID) {
        return WordUtils.capitalize(shieldID.replace("_", " "));
    }

}
//?}