package com.boruebork.fightforsupremacy.team;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record CapitalData(BlockPos pos, ResourceKey<Level> dimension, boolean isItem) {

}
