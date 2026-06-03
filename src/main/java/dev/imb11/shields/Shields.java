package dev.imb11.shields;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shields {
    public static final Logger LOGGER = LoggerFactory.getLogger("Shields");
    public static final String MOD_ID = "shields";

    public static Identifier of(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {

   }
}
