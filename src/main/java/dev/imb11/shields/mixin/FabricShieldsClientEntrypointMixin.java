package dev.imb11.shields.mixin;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLibClient;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import com.llamalad7.mixinextras.sugar.Local;
import dev.imb11.shields.enchantments.ShieldsEnchantmentKeys;
import dev.imb11.shields.items.BannerShieldItemWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(value = FabricShieldLibClient.class, remap = false)
public class FabricShieldsClientEntrypointMixin {
    @Inject(method = "lambda$onInitializeClient$0", at = @At(value = "INVOKE", target = "Lcom/github/crimsondawn45/fabricshieldlib/lib/object/FabricShield;displayTooltip()Z", remap = false), remap = false)
    private static void captureTooltipContext(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List tooltip, CallbackInfo ci, @Local FabricShield shield) {
        if (shield instanceof BannerShieldItemWrapper wrapper) {
            wrapper.MIXIN$ITEM_STACK_VALUE = stack;
            wrapper.ENCHANTMENT_LOOKUP = Objects.requireNonNull(context.registries()).lookupOrThrow(Registries.ENCHANTMENT);
        }
    }

    @Inject(method = "getCooldownTooltip", at = @At(value = "INVOKE", target = "Ljava/lang/String;toCharArray()[C", remap = false), remap = false)
    private static void addMomentumTooltip(ItemStack stack, TooltipFlag type, List<Component> tooltip, int cooldownTicks, CallbackInfoReturnable<List<Component>> cir) {
        if (stack.getItem() instanceof BannerShieldItemWrapper) {
            var lookup = Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            int momentumLevel = stack.getEnchantments().getLevel(lookup.getOrThrow(ShieldsEnchantmentKeys.MOMENTUM));
            if (momentumLevel > 0) {
                Component speedForXSeconds = Component.literal(" ").append(Component.translatable("enchantment.shields.momentum.tooltip_speed_for_x_seconds", String.format("%.1f", (60 + (8 * momentumLevel)) / 20.0F)).withStyle(ChatFormatting.DARK_GREEN));
                tooltip.add(speedForXSeconds);
            }
        }
    }
}
