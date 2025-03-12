package dev.imb11.shields.enchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;

public class ShieldsEnchantmentKeys {
    @ApiStatus.Internal
    public static final ArrayList<ResourceKey<Enchantment>> REGISTERED_ENCHANTMENTS = new ArrayList<>();
    public static final ResourceKey<Enchantment> EVOKERING = of("evokering");
    public static final ResourceKey<Enchantment> LAUNCHING = of("launching");
    public static final ResourceKey<Enchantment> LIFEBOUND = of("lifebound");
    public static final ResourceKey<Enchantment> MOMENTUM = of("momentum");
    public static final ResourceKey<Enchantment> BRACING = of("bracing");

    private static ResourceKey<Enchantment> of(String path) {
        ResourceLocation id = ResourceLocation.tryBuild("shields", path);
        var key = ResourceKey.create(Registries.ENCHANTMENT, id);
        REGISTERED_ENCHANTMENTS.add(key);
        return key;
    }
}
