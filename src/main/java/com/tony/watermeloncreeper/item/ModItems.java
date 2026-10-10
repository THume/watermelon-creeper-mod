
package com.tony.watermeloncreeper.item;

import com.tony.watermeloncreeper.entity.ModEntities;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class ModItems {

    private static final ResourceKey<Item> SPAWN_EGG_KEY =
        ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(
                "watermelon-creeper",
                "watermelon_creeper_spawn_egg"
            )
        );

    public static final Item WATERMELON_CREEPER_SPAWN_EGG =
        Registry.register(
            BuiltInRegistries.ITEM,
            SPAWN_EGG_KEY,
            new SpawnEggItem(
                new Item.Properties()
                    .setId(SPAWN_EGG_KEY)
                    .spawnEgg(ModEntities.WATERMELON_CREEPER)
            )
        );

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(
            CreativeModeTabs.SPAWN_EGGS
        ).register(tab -> {
            tab.accept(WATERMELON_CREEPER_SPAWN_EGG);
        });
    }
}
