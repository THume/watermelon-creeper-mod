
package com.tony.watermeloncreeper.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.gamerules.GameRules;

public class WatermelonCreeperEntity extends Creeper {

    public WatermelonCreeperEntity(
            EntityType<? extends Creeper> type,
            Level level
    ) {
        super(type, level);
    }

    public void explodeWatermelon() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        // Reduced explosion strength.
        float radius = this.isPowered() ? 3.0F : 1.5F;

        // Prevent the explosion from damaging the Creeper itself.
        this.dead = true;

        // Damage entities without destroying terrain.
        serverLevel.explode(
            this,
            this.getX(),
            this.getY(),
            this.getZ(),
            radius,
            Level.ExplosionInteraction.NONE
        );

        // Transform nearby surface blocks into watermelons.
        this.transformSurfaceToMelons(serverLevel);

        // Display red and green explosion particles.
        this.spawnMelonParticles(serverLevel);

        // Scatter collectible melon slices.
        this.scatterMelonSlices(serverLevel);

        // Remove the Watermelon Creeper.
        this.discard();
    }

    private void scatterMelonSlices(ServerLevel serverLevel) {
        int count = 20 + this.random.nextInt(11);

        for (int i = 0; i < count; i++) {
            ItemStack stack = new ItemStack(Items.MELON_SLICE);

            ItemEntity slice = new ItemEntity(
                serverLevel,
                this.getX(),
                this.getY() + 0.5,
                this.getZ(),
                stack
            );

            // Choose a random outward direction.
            double angle = this.random.nextDouble()
                    * Math.PI * 2.0;

            double speed = 0.15
                    + this.random.nextDouble() * 0.30;

            double velocityX = Math.cos(angle) * speed;
            double velocityY = 0.25
                    + this.random.nextDouble() * 0.30;
            double velocityZ = Math.sin(angle) * speed;

            slice.setDeltaMovement(
                velocityX,
                velocityY,
                velocityZ
            );

            serverLevel.addFreshEntity(slice);
        }
    }

    private void spawnMelonParticles(ServerLevel serverLevel) {

        // Watermelon flesh: bright red.
        DustParticleOptions redParticles =
            new DustParticleOptions(0xE83E45, 1.5F);

        // Watermelon rind: dark green.
        DustParticleOptions greenParticles =
            new DustParticleOptions(0x2E8B42, 1.3F);

        double x = this.getX();
        double y = this.getY() + 0.8;
        double z = this.getZ();

        // Burst of red watermelon flesh.
        serverLevel.sendParticles(
            redParticles,
            x, y, z,
            80,       // Particle count
            0.7,      // X spread
            0.6,      // Y spread
            0.7,      // Z spread
            0.15      // Particle speed
        );

        // Smaller burst of green rind fragments.
        serverLevel.sendParticles(
            greenParticles,
            x, y, z,
            35,
            0.8,
            0.7,
            0.8,
            0.20
        );
    }

    private void transformSurfaceToMelons(ServerLevel serverLevel) {

        // Respect the world's mob griefing setting.
        if (!serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) {
            return;
        }

        final int radius = 5;
        final int radiusSquared = radius * radius;

        int centerX = this.blockPosition().getX();
        int centerY = this.blockPosition().getY() - 1;
        int centerZ = this.blockPosition().getZ();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {

                // Keep the transformation circular.
                if (dx * dx + dz * dz > radiusSquared) {
                    continue;
                }

                int x = centerX + dx;
                int z = centerZ + dz;

                // Find the highest solid surface in this column.
                int surfaceY = serverLevel.getHeight(
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    x,
                    z
                ) - 1;

                // Avoid altering surfaces far above or below
                // the Creeper's explosion.
                if (Math.abs(surfaceY - centerY) > 3) {
                    continue;
                }

                BlockPos pos = new BlockPos(x, surfaceY, z);
                BlockState state = serverLevel.getBlockState(pos);

                // Protect special blocks and fluids.
                if (state.isAir()
                        || state.is(Blocks.BEDROCK)
                        || state.is(Blocks.MELON)
                        || state.hasBlockEntity()
                        || !state.getFluidState().isEmpty()) {
                    continue;
                }

                // Replace the surface block with watermelon.
                serverLevel.setBlock(
                    pos,
                    Blocks.MELON.defaultBlockState(),
                    3
                );
            }
        }
    }
}
