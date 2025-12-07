//? fabric {
package dev.imb11.shields.datagen;

import dev.imb11.shields.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.Nullable;

public class ShieldsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(ShieldsAtlasProvider::new);
//        pack.addProvider(ShieldsModelProvider::new);
        pack.addProvider(ShieldsItemTagProvider::new);
        pack.addProvider(ShieldsEnchantmentTagProvider::new);
        pack.addProvider(ShieldsEnchantmentProvider::new);
        pack.addProvider(ShieldsRecipeProvider::new);
        pack.addProvider(ShieldsLangProvider::new);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return "shields";
    }
}
//?}