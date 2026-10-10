package com.tony.watermeloncreeper.client;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;

public class WatermelonCreeperRenderer extends CreeperRenderer {

    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(
            "watermelon-creeper",
            "textures/entity/watermelon_creeper.png"
        );

    public WatermelonCreeperRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(
            CreeperRenderState state
    ) {
        return TEXTURE;
    }
}