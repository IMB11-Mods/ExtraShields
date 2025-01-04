package dev.imb11.shields.items;

import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ShieldsClient;
import dev.imb11.shields.datagen.providers.ShieldsEnchantmentProvider;
import dev.imb11.shields.items.custom.ShieldPatchKitItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ShieldsItems {
    @ApiStatus.Internal
    public static final ArrayList<BannerShieldItemWrapper> SHIELD_ITEMS = new ArrayList<>();
    @ApiStatus.Internal
    public static final ArrayList<Item> SHIELD_PLATING_ITEMS = new ArrayList<>();

    public static final BannerShieldItemWrapper PLATED_SHIELD;
    public static final BannerShieldItemWrapper DIAMOND_SHIELD;
    public static final BannerShieldItemWrapper PLATED_DIAMOND_SHIELD;
    public static final BannerShieldItemWrapper COPPER_SHIELD;
    public static final BannerShieldItemWrapper PLATED_COPPER_SHIELD;
    public static final BannerShieldItemWrapper GOLD_SHIELD;
    public static final BannerShieldItemWrapper PLATED_GOLD_SHIELD;
    public static final BannerShieldItemWrapper NETHERITE_SHIELD;
    public static final BannerShieldItemWrapper PLATED_NETHERITE_SHIELD;
    public static final Item SHIELD_PLATING;
    public static final Item GOLD_SHIELD_PLATING;
    public static final Item DIAMOND_SHIELD_PLATING;
    public static final Item NETHERITE_SHIELD_PLATING;
    public static final Item COPPER_SHIELD_PLATING;
    public static final Item SHIELD_REPAIR_KIT;

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY;
    public static final CreativeModeTab CUSTOM_ITEM_GROUP;
    // [plating (2nd input)], [1st input, output]
    @ApiStatus.Experimental
    public static final Map<Item, Item[]> PLATING_UPGRADE_MAP;

    static {
        PLATED_SHIELD = create("plated_shield", 420, 6 * 20, ItemTags.PLANKS);
        DIAMOND_SHIELD = create("diamond_shield", 867, 4 * 20, Items.DIAMOND);
        PLATED_DIAMOND_SHIELD = create("plated_diamond_shield", 1083, 5 * 20, Items.DIAMOND);
        GOLD_SHIELD = create("gold_shield", 451, 3 * 20, Items.GOLD_INGOT);
        PLATED_GOLD_SHIELD = create("plated_gold_shield", 563, 4 * 20, Items.GOLD_INGOT);
        NETHERITE_SHIELD = create("netherite_shield", 910, 5 * 20, Items.NETHERITE_INGOT);
        PLATED_NETHERITE_SHIELD = create("plated_netherite_shield", 1137, 6 * 20, Items.NETHERITE_INGOT);
        COPPER_SHIELD = create("copper_shield", 240, 8 * 20, Items.COPPER_INGOT);
        PLATED_COPPER_SHIELD = create("plated_copper_shield", 300, 10 * 20, Items.COPPER_INGOT);

        SHIELD_PLATING = register("shield_plating", Item::new);
        GOLD_SHIELD_PLATING = register("gold_shield_plating", Item::new);
        DIAMOND_SHIELD_PLATING = register("diamond_shield_plating", Item::new);
        NETHERITE_SHIELD_PLATING = register("netherite_shield_plating", Item::new);
        COPPER_SHIELD_PLATING = register("copper_shield_plating", Item::new);

        SHIELD_REPAIR_KIT = register("shield_repair_kit", (properties) -> new ShieldPatchKitItem(properties.durability(4)));

        SHIELD_PLATING_ITEMS.addAll(
                List.of(
                        SHIELD_PLATING,
                        GOLD_SHIELD_PLATING,
                        DIAMOND_SHIELD_PLATING,
                        NETHERITE_SHIELD_PLATING,
                        COPPER_SHIELD_PLATING
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
                        PLATED_NETHERITE_SHIELD,
                        COPPER_SHIELD,
                        PLATED_COPPER_SHIELD
                )
        );

        CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.tryParse("shields:item_group"));

        CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(GOLD_SHIELD))
                .title(Component.translatable("itemGroup.shields.shield_group"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.acceptAll(SHIELD_PLATING_ITEMS.stream().map(Item::getDefaultInstance).toList());
                    output.accept(SHIELD_REPAIR_KIT);
                    output.accept(Items.SHIELD);
                    output.acceptAll(SHIELD_ITEMS.stream().map(Item::getDefaultInstance).toList());

                    itemDisplayParameters.holders().lookup(Registries.ENCHANTMENT).ifPresent(enchantments -> {
                        for (ResourceKey<Enchantment> registeredEnchantment : ShieldsEnchantmentProvider.REGISTERED_ENCHANTMENTS) {
                            var reference = enchantments.getOrThrow(registeredEnchantment);
                            IntStream.rangeClosed(
                                    reference.value().getMinLevel(),
                                    reference.value().getMaxLevel()
                            ).mapToObj(level ->
                                    EnchantedBookItem.createForEnchantment(new EnchantmentInstance(reference, level))
                            ).forEach(itemStack ->
                                    output.accept(itemStack, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY)
                            );
                        }
                    });
                })
                .build();

        PLATING_UPGRADE_MAP = Map.of(
                SHIELD_PLATING, new Item[]{Items.SHIELD, PLATED_SHIELD},
                GOLD_SHIELD_PLATING, new Item[]{GOLD_SHIELD, PLATED_COPPER_SHIELD},
                DIAMOND_SHIELD_PLATING, new Item[]{DIAMOND_SHIELD, PLATED_DIAMOND_SHIELD},
                NETHERITE_SHIELD_PLATING, new Item[]{NETHERITE_SHIELD, PLATED_NETHERITE_SHIELD},
                COPPER_SHIELD_PLATING, new Item[]{COPPER_SHIELD, PLATED_COPPER_SHIELD}
        );
    }

    public static void initialize() {
        Shields.LOGGER.info("Initializing ShieldItems");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);
    }

    private static BannerShieldItemWrapper create(String id, int durability, int blockingDelay, Item... repairItems) {
        var item = register(id, (settings) -> new BannerShieldItemWrapper(settings.durability(durability), blockingDelay, 9, repairItems));

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ShieldsClient.registerDynamicShield(id, item);
        }

        return item;
    }

    private static BannerShieldItemWrapper create(String id, int durability, int blockingDelay, TagKey<Item> repairItems) {
        var item = register(id, (settings) -> new BannerShieldItemWrapper(settings.durability(durability), blockingDelay, 9, repairItems));

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
