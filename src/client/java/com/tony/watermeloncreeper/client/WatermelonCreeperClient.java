package com.tony.watermeloncreeper.client;

import net.fabricmc.api.ClientModInitializer;

import com.tony.watermeloncreeper.entity.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class WatermelonCreeperClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(
			ModEntities.WATERMELON_CREEPER,
			WatermelonCreeperRenderer::new
		);
	}
}