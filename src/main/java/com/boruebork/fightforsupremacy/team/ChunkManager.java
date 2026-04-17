package com.boruebork.fightforsupremacy.team;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.List;
import java.util.Optional;
@EventBusSubscriber(modid = FightForSupremacy.MODID)
public class ChunkManager {
    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event){
        List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
        for (ServerPlayer player : players){
            BlockPos pos = player.getOnPos();
            ChunkPos chunkPos = new ChunkPos(pos);
            Level level = event.getServer().getLevel(Level.OVERWORLD);
            List<Player> playersInChunk = level.getEntitiesOfClass(Player.class, AABBfromChunkPos(chunkPos));
            //Check if players from one team in chunk

            Optional<Team> team = TeamUtil.areAllPlayersFromOneTeam(playersInChunk);
            if (team == null) continue;
            if (team.isEmpty()){
                continue;
            }
            if (team.isPresent()){
                claimChunk(team.get(), event.getServer(), Level.OVERWORLD, chunkPos);
            }
        }
    }
    public static AABB AABBfromChunkPos(ChunkPos chunkPos){
        int x = chunkPos.getMaxBlockX();
        int z = chunkPos.getMaxBlockZ();
        AABB toReturn = new AABB(x, 384, z, x - 15, -65, z - 15);
        return toReturn;

    }
    public static void claimChunk(Team team, MinecraftServer server, ResourceKey<Level> dimension, ChunkPos chunkPos){
        ClaimedChunkManager Cmanager = FTBChunksAPI.api().getManager();
        ChunkDimPos chunkDimPos = new ChunkDimPos(dimension, chunkPos);
        Cmanager.getOrCreateData(team).claim(TeamUtil.fakeSource(server), chunkDimPos, false);
    }
}