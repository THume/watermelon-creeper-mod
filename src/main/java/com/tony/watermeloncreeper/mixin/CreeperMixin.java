
package com.tony.watermeloncreeper.mixin;

import com.tony.watermeloncreeper.entity.WatermelonCreeperEntity;

import net.minecraft.world.entity.monster.Creeper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin {

    @Inject(
        method = "explodeCreeper",
        at = @At("HEAD"),
        cancellable = true
    )
    private void watermeloncreeper$onExplode(
            CallbackInfo ci
    ) {
        if ((Object) this instanceof WatermelonCreeperEntity watermelon) {
            watermelon.explodeWatermelon();

            // Prevent the vanilla explosion.
            ci.cancel();
        }
    }
}
