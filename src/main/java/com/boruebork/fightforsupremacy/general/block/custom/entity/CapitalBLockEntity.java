package com.boruebork.fightforsupremacy.general.block.custom.entity;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class CapitalBLockEntity extends BlockEntity {
    public int health = 500;
    public UUID team;

    //For BER
    public String teamName;

    public CapitalBLockEntity(BlockPos pos, BlockState blockState) {
        super(ModBE.CAPITAL_BE.get(), pos, blockState);
    }


    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide()) return;
        if (!FTBTeamsAPI.api().getManager().getTeamByID(team).isEmpty()){
            teamName = FTBTeamsAPI.api().getManager().getTeamByID(team).get().getName().getString();
        }
    }
}
