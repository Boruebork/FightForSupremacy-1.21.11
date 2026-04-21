package com.boruebork.fightforsupremacy.general.block.custom.entity;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.general.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBE {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FightForSupremacy.MODID);

    public static final Supplier<BlockEntityType<CapitalBLockEntity>> CAPITAL_BE =
            BLOCK_ENTITIES.register("capital_be", () -> new BlockEntityType<>(
                    CapitalBLockEntity::new, ModBlocks.CAPITAL.get()));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
