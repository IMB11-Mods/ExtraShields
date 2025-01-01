package dev.imb11.shields.mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldLibDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = FabricShieldLibDataGenerator.class, remap = false)
public class ShieldsLibDatagenYeet {
    @Inject(method = "onInitializeDataGenerator", at = @At("HEAD"), cancellable = true, remap = false)
    public void cancelDataGen(FabricDataGenerator generator, CallbackInfo ci) {
        ci.cancel();
    }
}
