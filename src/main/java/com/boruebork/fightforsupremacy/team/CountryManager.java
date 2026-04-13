package com.boruebork.fightforsupremacy.team;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.data.PartyTeam;
import dev.ftb.mods.ftbteams.data.ServerTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CountryManager {
    public static List<Country> countries = new ArrayList<>();
    public static Country createCountry(ServerPlayer player, String name, String description, Color4I color) throws CommandSyntaxException {
        countries.add(new Country((PartyTeam) ((TeamManagerImpl) FTBTeamsAPI.api().getManager()).createParty(player.getUUID(), player, name, name, color)));
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
