
package com.tony.watermeloncreeper.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.world.entity.MobCategory;

public class ModSpawns {

    private static final int SPAWN_WEIGHT = 100;
    private static final int MIN_GROUP_SIZE = 1;
    private static final int MAX_GROUP_SIZE = 1;

    public static void initialize() {

        BiomeModifications.addSpawn(
            BiomeSelectors.foundInOverworld(),
            MobCategory.MONSTER,
            ModEntities.WATERMELON_CREEPER,
            SPAWN_WEIGHT,
            MIN_GROUP_SIZE,
            MAX_GROUP_SIZE
        );
    }
}
