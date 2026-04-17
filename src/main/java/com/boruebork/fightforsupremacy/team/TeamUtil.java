package com.boruebork.fightforsupremacy.team;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamManager;
import dev.ftb.mods.ftbteams.data.ServerTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.entity.player.Player;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamUtil {
    public static CommandSourceStack fakeSource(MinecraftServer server) {
        return server.createCommandSourceStack()
                .withSuppressedOutput() // optional: no chat spam
                .withPermission(PermissionSet.ALL_PERMISSIONS);     // OP level (important!)
    }

    public static Team createCountry(MinecraftServer server, String name, Color4I color) throws CommandSyntaxException {
        TeamManager manager = FTBTeamsAPI.api().getManager();
        TeamManagerImpl managerImpl = (TeamManagerImpl) manager;
        return managerImpl.createServerTeam(fakeSource(server), name, name, color);
    }
    public static Optional<Team> areAllPlayersFromOneTeam(List<Player> players){
        for (int i = 1; i < players.size(); ++i){
            if (!FTBTeamsAPI.api().getManager().arePlayersInSameTeam(players.get(i -1).getUUID(), players.get(i).getUUID())){
                return null;
            }
        }
        if (players.isEmpty()) return null;
        return FTBTeamsAPI.api().getManager().getTeamForPlayerID(players.getFirst().getUUID());
    }
}
