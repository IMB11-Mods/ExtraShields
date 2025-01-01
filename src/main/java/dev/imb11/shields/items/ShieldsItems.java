package dev.imb11.shields.items;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ShieldsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ShieldsItems {
    @ApiStatus.Internal
    public static final ArrayList<FabricBannerShieldItem> SHIELD_ITEMS = new ArrayList<>();
    @ApiStatus.Internal
    public static final ArrayList<Item> SHIELD_PLATING_ITEMS = new ArrayList<>();

    public static final FabricBannerShieldItem PLATED_SHIELD;
    public static final FabricBannerShieldItem DIAMOND_SHIELD;
    public static final FabricBannerShieldItem PLATED_DIAMOND_SHIELD;
    public static final FabricBannerShieldItem GOLD_SHIELD;
    public static final FabricBannerShieldItem PLATED_GOLD_SHIELD;
    public static final FabricBannerShieldItem NETHERITE_SHIELD;
    public static final FabricBannerShieldItem PLATED_NETHERITE_SHIELD;
    public static final Item SHIELD_PLATING;
    public static final Item GOLD_SHIELD_PLATING;
    public static final Item DIAMOND_SHIELD_PLATING;
    public static final Item NETHERITE_SHIELD_PLATING;

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY;
    public static final CreativeModeTab CUSTOM_ITEM_GROUP;

    static {
        PLATED_SHIELD = create("plated_shield", 420, 6 * 20, ItemTags.PLANKS);
        DIAMOND_SHIELD = create("diamond_shield", 867, 4 * 20, Items.DIAMOND);
        PLATED_DIAMOND_SHIELD = create("plated_diamond_shield", 1083, 5 * 20, Items.DIAMOND);
        GOLD_SHIELD = create("gold_shield", 451, 3 * 20, Items.GOLD_INGOT);
        PLATED_GOLD_SHIELD = create("plated_gold_shield", 563, 4 * 20, Items.GOLD_INGOT);
        NETHERITE_SHIELD = create("netherite_shield", 910, 5 * 20, Items.NETHERITE_INGOT);
        PLATED_NETHERITE_SHIELD = create("plated_netherite_shield", 1137, 6 * 20, Items.NETHERITE_INGOT);

        SHIELD_PLATING = register("shield_plating", Item::new);
        GOLD_SHIELD_PLATING = register("gold_shield_plating", Item::new);
        DIAMOND_SHIELD_PLATING = register("diamond_shield_plating", Item::new);
        NETHERITE_SHIELD_PLATING = register("netherite_shield_plating", Item::new);

        SHIELD_PLATING_ITEMS.addAll(
                List.of(
                        SHIELD_PLATING,
                        GOLD_SHIELD_PLATING,
                        DIAMOND_SHIELD_PLATING,
                        NETHERITE_SHIELD_PLATING
                )
        );

        SHIELD_ITEMS.addAll(
                List.of(
                        PLATED_SHIELD,
                        DIAMOND_SHIELD,
                        PLATED_DIAMOND_SHIELD,
                        GOLD_SHIELD,
                        PLATED_GOLD_SHIELD,
                        NETHERITE_SHIELD,
                        PLATED_NETHERITE_SHIELD
                )
        );

        CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.tryParse("shields:item_group"));

        CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(GOLD_SHIELD))
                .title(Component.translatable("itemGroup.shields.shield_group"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.acceptAll(SHIELD_PLATING_ITEMS.stream().map(Item::getDefaultInstance).toList());
                    output.acceptAll(SHIELD_ITEMS.stream().map(Item::getDefaultInstance).toList());
                })
                .build();
    }

    public static void initialize() {
        Shields.LOGGER.info("Initializing ShieldItems");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);
    }

    private static FabricBannerShieldItem create(String id, int durability, int blockingDelay, Item... repairItems) {
        var item = register(id, (settings) -> new FabricBannerShieldItem(settings.durability(durability), blockingDelay, 9, repairItems));

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ShieldsClient.registerDynamicShield(id, item);
        }

        return item;
    }

    private static FabricBannerShieldItem create(String id, int durability, int blockingDelay, TagKey<Item> repairItems) {
        var item = register(id, (settings) -> new FabricBannerShieldItem(settings.durability(durability), blockingDelay, 9, repairItems));

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ShieldsClient.registerDynamicShield(id, item);
        }

        return item;
    }

    private static <T extends Item> T register(String id, Function<Item.Properties, T> builder) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.tryBuild("shields", id));

        return Registry.register(BuiltInRegistries.ITEM, key, builder.apply(new Item.Properties()));
    }
}
