package com.boruebork.fightforsupremacy.team;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.data.ServerTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public class CountryManager {
    public static List<Country> countries;
    public static Country createCountry(MinecraftServer server, String name, String description, Color4I color) throws CommandSyntaxException {
        countries.add(new Country((ServerTeam) ((TeamManagerImpl) FTBTeamsAPI.api().getManager()).createServerTeam(TeamUtil.fakeSource(server), name, description, color, UUID.randomUUID())));
        return countries.getLast();
    }
    public static Country findPlayerCountry(Player player){
        for (Country country : countries){
            if (country.team.isMember(player.getUUID())){
                return country;
            }
        }
        return null;
    }
}
