package dev.imb11.shields;

//? fabric {
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
//?} else {
/*import net.neoforged.fml.loading.FMLEnvironment;
*///?}

public interface Platform {
    static boolean isClient() {
        //? if neoforge
        //return FMLEnvironment.getDist().isClient();
        //? if fabric
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
