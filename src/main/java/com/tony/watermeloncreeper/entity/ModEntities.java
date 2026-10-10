
package com.tony.watermeloncreeper.entity;

import com.tony.watermeloncreeper.WatermelonCreeper;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Creeper;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntities {

    private static final ResourceKey<EntityType<?>> KEY =
        ResourceKey.create(
            Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(
                WatermelonCreeper.MOD_ID,
                "watermelon_creeper"
            )
        );


    public static final EntityType<WatermelonCreeperEntity>
        WATERMELON_CREEPER = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            KEY,

            FabricEntityType.Builder
                .<WatermelonCreeperEntity>createMob(
                    WatermelonCreeperEntity::new,
                    MobCategory.MONSTER,
                    builder -> builder.spawnPlacement(
                        SpawnPlacementTypes.ON_GROUND,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        Monster::checkMonsterSpawnRules
                    )
                )
                .sized(0.6f, 1.7f)
                .build(KEY)
    );


    public static void initialize() {
        FabricDefaultAttributeRegistry.register(
            WATERMELON_CREEPER,
            Creeper.createAttributes()
        );
    }
}
