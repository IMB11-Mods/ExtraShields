//? fabric {
package dev.imb11.shields.fabric;

import dev.imb11.shields.Shields;
import dev.imb11.shields.client.ExtraShieldSpecialRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class ShieldsFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SpecialModelRenderers.ID_MAPPER.put(Shields.of("shields"), ExtraShieldSpecialRenderer.Unbaked.MAP_CODEC);
	}
}
//?}
