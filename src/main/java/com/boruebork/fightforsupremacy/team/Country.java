package com.boruebork.fightforsupremacy.team;

import dev.ftb.mods.ftbteams.data.ServerTeam;
import net.minecraft.resources.Identifier;

public class Country {
    public ServerTeam team;
    public Identifier flag;
    public CountryData data;
    public Country(ServerTeam team){
        this.team = team;
    }

}
