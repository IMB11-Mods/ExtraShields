package dev.imb11.shields.mixin;

import dev.imb11.shields.enchantments.ShieldsEnchantmentEffects;
import dev.imb11.shields.items.ShieldsItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(
			at = @At(
					value = "INVOKE",
					shift = At.Shift.AFTER,
					target = "Lnet/minecraft/world/item/component/BlocksAttacks;resolveBlockedDamage(Lnet/minecraft/world/damagesource/DamageSource;FD)F"
			),
			method = "applyItemBlocking(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)F"
	)
	private void triggerBlockEvent(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Float> cir) {
		LivingEntity self = (LivingEntity) (Object) this;
		ShieldsEnchantmentEffects.eventShieldBlock(level, self, source, self.getItemBlockingWith());
	}

	@Inject(
			at = @At("HEAD"),
			method = "blockUsingItem",
			cancellable = true
	)
	private void triggerDisabledEvent(ServerLevel level, LivingEntity attacker, CallbackInfo ci) {

		LivingEntity self = (LivingEntity) (Object) this;
		boolean isPlayer = self instanceof Player;

		if(isPlayer) {

			ItemStack shield = self.getItemBlockingWith();
			BlocksAttacks blocksAttacks = shield != null ? shield.get(DataComponents.BLOCKS_ATTACKS) : null;

			float secondsToDisable = attacker.getSecondsToDisableBlocking();

			if (secondsToDisable > 0 && blocksAttacks != null) {

				ShieldsEnchantmentEffects.eventShieldDisabled(level, attacker, self, shield);

				HolderSet.Named<Item> holders = BuiltInRegistries.ITEM.get(ShieldsItemTags.CONVENTIONAL_SHIELDS).get();
				secondsToDisable = ShieldsEnchantmentEffects.getModifiedCooldown(level, attacker, shield, secondsToDisable);

				for(Holder<Item> holder : holders) {
					blocksAttacks.disable(level, self, secondsToDisable, new ItemStack(holder.value()));
				}

				ci.cancel();

			}
		}
	}
}
