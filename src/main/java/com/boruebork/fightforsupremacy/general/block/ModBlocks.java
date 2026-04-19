package com.boruebork.fightforsupremacy.general.block;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.general.block.custom.CapitalBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FightForSupremacy.MODID);

    public static final DeferredBlock<Block> CAPITAL = BLOCKS.registerBlock("capital", (CapitalBlock::new));

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
