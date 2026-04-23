package com.boruebork.fightforsupremacy.general.entity;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.general.entity.custom.RadarEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(FightForSupremacy.MODID);

    private static final ResourceKey<EntityType<?>> RADAR_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.withDefaultNamespace("radar"));
    public static final Supplier<EntityType<RadarEntity>> RADAR = ENTITIES.register("radar",
            () -> EntityType.Builder.of(
                    RadarEntity::new,
                    MobCategory.MISC
            ).build(RADAR_KEY));
    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
