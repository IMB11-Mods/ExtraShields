package dev.imb11.shields.mixin;

import java.util.Objects;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.imb11.shields.client.ShieldsClient;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ShieldSpecialRenderer.class)
public abstract class ShieldSpecialRendererMixin implements ResourceManagerReloadListener {
    @Shadow @Final private MaterialSet materials;

    @Shadow
    @Final
    private ShieldModel model;

    /**
     * Credits to <a href="https://github.com/pnk2u/More-Shield-Variants/blob/1.21.5(-8)/src/client/java/de/pnku/lolmsv/mixin/client/ShieldSpecialRendererMixin.java">More Shield Variant's Implementation of Shield Rendering</a>
     * for the correct mixin target.
     */
    @WrapMethod(method = "submit(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IIZI)V")
    private void injectedSubmit(DataComponentMap dataComponentMap, ItemDisplayContext type, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor, Operation<Void> original) {
        Component itemNameComponent = dataComponentMap.get(DataComponents.ITEM_NAME);
        if (itemNameComponent.getContents() instanceof TranslatableContents translatableContents) {
            var key = translatableContents.getKey();
            if (key.contains("item.shields")) {
                var material = translatableContents.getKey().replace("item.shields.", "");
                BannerPatternLayers bannerPatternsComponent = dataComponentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
                DyeColor shieldBannerDyeColor = dataComponentMap.get(DataComponents.BASE_COLOR);
                boolean hasBanner = !bannerPatternsComponent.layers().isEmpty() || shieldBannerDyeColor != null;
                poseStack.pushPose();
                poseStack.scale(1.0f, -1.0f, -1.0f);

                var materials = ShieldsClient.REGISTERED_MATERIALS.get(material);
                Material shieldBaseTextureLocation = materials.get(0);
                Material noPatternShieldBaseTextureLocation = materials.get(1);
                Material spriteIdentifier = hasBanner ? shieldBaseTextureLocation : noPatternShieldBaseTextureLocation;
                submitNodeCollector.submitModelPart(this.model.handle(), poseStack, this.model.renderType(spriteIdentifier.atlasLocation()), lightCoords, overlayCoords, this.materials.get(spriteIdentifier), false, false, -1,(ModelFeatureRenderer.CrumblingOverlay) null, outlineColor);
                if (hasBanner) {
                    BannerRenderer.submitPatterns(
                            this.materials,
                            poseStack,
                            submitNodeCollector,
                            lightCoords,
                            overlayCoords,
                            this.model,
                            Unit.INSTANCE,
                            shieldBaseTextureLocation,
                            false,
							Objects.requireNonNullElse(shieldBannerDyeColor, DyeColor.WHITE),
                            bannerPatternsComponent,
                            hasFoil,
                            null,
                            outlineColor
                    );
                }
                else {
                    submitNodeCollector.submitModelPart(this.model.plate(), poseStack, this.model.renderType(spriteIdentifier.atlasLocation()), lightCoords, overlayCoords, this.materials.get(spriteIdentifier), false, hasFoil, -1,(ModelFeatureRenderer.CrumblingOverlay) null, outlineColor);
                }
                poseStack.popPose();
            } else {
                original.call(dataComponentMap, type, poseStack, submitNodeCollector, lightCoords, overlayCoords, hasFoil, outlineColor);
            }
        }
    }
}