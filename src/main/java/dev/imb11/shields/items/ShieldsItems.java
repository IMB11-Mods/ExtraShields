package dev.imb11.shields.items;

import dev.imb11.shields.Platform;
import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ShieldsClient;
import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import dev.imb11.shields.items.custom.ShieldPatchKitItem;
//? fabric {
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
//?}
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShieldsItems {
    @ApiStatus.Internal
    public static final LinkedHashMap<String, ShieldCollection> SHIELD_COLLECTIONS = new LinkedHashMap<>();

    public static final ShieldPatchKitItem SHIELD_REPAIR_KIT = register("shield_repair_kit", (properties) -> new ShieldPatchKitItem(properties.durability(4)));

    public static final ShieldCollection IRON = createCollection("iron", (ShieldItem) Items.SHIELD, Items.SHIELD.builtInRegistryHolder().key(), 300, ItemTags.IRON_TOOL_MATERIALS, 1.0f);
    public static final ShieldCollection COPPER = createCollection("copper", 240, 300, ItemTags.COPPER_TOOL_MATERIALS, 1.1f);
    public static final ShieldCollection DIAMOND = createCollection("diamond", 867, 1083, ItemTags.DIAMOND_TOOL_MATERIALS, 0.75f);
    public static final ShieldCollection GOLD = createCollection("gold", 451, 563, ItemTags.GOLD_TOOL_MATERIALS, 0.5f);
    public static final ShieldCollection NETHERITE = createCollection("netherite", 910, 1137, ItemTags.NETHERITE_TOOL_MATERIALS, 1.1f);

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(Shields.MOD_ID, "item_group"));
    public static final CreativeModeTab CUSTOM_ITEM_GROUP;

    static {

        CUSTOM_ITEM_GROUP =
                //? fabric {
                FabricCreativeModeTab.builder()
                //?} else {
                /*CreativeModeTab.builder()
                *///?}
                .icon(() -> new ItemStack(GOLD.shieldItem()))
                .title(Component.translatable("itemGroup.shields.shield_group"))
                .displayItems((itemDisplayParameters, output) -> {

                    // Output in rows of material.
                    // Fill gap with enchantments.
                    for (ShieldCollection shieldCollection : SHIELD_COLLECTIONS.values()) {
                        output.accept(shieldCollection.shieldItem());
                        output.accept(shieldCollection.plating());
                        output.accept(shieldCollection.platedShieldItem());
                    }
                    output.accept(SHIELD_REPAIR_KIT);

					itemDisplayParameters.holders().lookup(Registries.ENCHANTMENT).ifPresent(enchantments -> {
                        for (ResourceKey<Enchantment> registeredEnchantment : ShieldsEnchantmentKeys.REGISTERED_ENCHANTMENTS) {
                            var reference = enchantments.get(registeredEnchantment);
                            if (reference.isPresent()) {
                                var book = EnchantmentHelper.createBook(new EnchantmentInstance(reference.get(), reference.get().value().getMaxLevel()));
                                output.accept(book);
                            }
                        }
                    });
                })
                .build();
    }

    public static void initialize() {
        Shields.LOGGER.info("Initializing ShieldItems");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);
    }

    private static ShieldCollection createCollection(String id, int durability, int platedDurability, TagKey<Item> repairIngredient, float cooldownModifier) {
        var shieldKey = key(id + "_shield");
        var shield = create(shieldKey, durability, repairIngredient, cooldownModifier);
		return createCollection(id, shield, shieldKey, platedDurability, repairIngredient, cooldownModifier);
    }

    private static ShieldCollection createCollection(String id, ShieldItem shield, ResourceKey<Item> shieldKey, int platedDurability, TagKey<Item> repairIngredient, float cooldownModifier) {
        var prefix = id;
        if (id.equals("iron")) {
            prefix = "";
        }

        // plated shield
        var platedShieldKey = key(add("plated", prefix, "shield"));
        var platedShield = create(platedShieldKey, platedDurability, repairIngredient, cooldownModifier*2);
        // shield plating
        var shieldPlatingKey = key(add(prefix, "shield", "plating"));
        var plating = register(shieldPlatingKey, Item::new);
        // textures
        Identifier shieldBaseTexture = (Shields.of(add(id, "shield_base")));
        Identifier shieldBaseNoPatternTexture = (Shields.of(add(id, "shield_base_nopattern")));
        Identifier platedShieldBaseTexture = (Shields.of(add("plated", prefix, "shield_base")));
        Identifier platedShieldBaseNoPatternTexture = (Shields.of(add("plated", prefix, "shield_base_nopattern")));
        // collection
        var c = new ShieldCollection(shield, platedShield, plating, shieldKey, platedShieldKey, shieldPlatingKey, shieldBaseTexture, shieldBaseNoPatternTexture, platedShieldBaseTexture, platedShieldBaseNoPatternTexture);
        SHIELD_COLLECTIONS.put(id, c);
        return c;
    }

	private static String add(String... strings) {
		return String.join("_", Arrays.stream(strings).filter(s->!s.isEmpty()).toArray(String[]::new));
	}

    private static ResourceKey<Item> key(String id) {
        return ResourceKey.create(Registries.ITEM, Shields.of(id));
    }

    private static ResourceKey<Item> key(Identifier id) {
        return ResourceKey.create(Registries.ITEM, id);
    }

    private static ShieldItem create(ResourceKey<Item> id, int durability, TagKey<Item> repairItems, float cooldownScale) {

        var item = register(id, (settings) -> new ShieldItem(settings
                .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                .enchantable(9)
                .equippableUnswappable(EquipmentSlot.OFFHAND)
                .delayedComponent(DataComponents.BLOCKS_ATTACKS, (context) -> new BlocksAttacks(0.25F, cooldownScale, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)), new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)), Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK).durability(durability)
                .durability(durability)
                .repairable(repairItems)
                .setId(id)));

        if (Platform.isClient()) {
            ShieldsClient.registerDynamicShield(id.identifier().getPath());
        }

        return item;
    }

    private static <T extends Item> T register(ResourceKey<Item> key, Function<Item.Properties, T> builder) {
        return Registry.register(BuiltInRegistries.ITEM, key, builder.apply(new Item.Properties().setId(key)));
    }

    private static <T extends Item> T register(String id, Function<Item.Properties, T> builder) {
        ResourceKey<Item> key = key(Identifier.fromNamespaceAndPath(Shields.MOD_ID, id));

        return register(key, builder);
    }

	public static Collection<ShieldItem> shieldItems(boolean includeVanilla) {
        Collection<ShieldItem> list = new ArrayList<>();
		SHIELD_COLLECTIONS.values().forEach(c->{
            if (includeVanilla || !c.shieldItemKey().identifier().getNamespace().equals("minecraft"))
                list.add(c.shieldItem());
            list.add(c.platedShieldItem());
        });
        return list;
	}
}
