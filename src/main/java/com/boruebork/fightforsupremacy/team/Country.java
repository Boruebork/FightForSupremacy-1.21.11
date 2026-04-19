package com.boruebork.fightforsupremacy.team;

import dev.ftb.mods.ftbteams.data.PartyTeam;
import dev.ftb.mods.ftbteams.data.ServerTeam;
import net.minecraft.resources.Identifier;

public class Country {
    public CapitalData capitalData;
    public PartyTeam team;
    public Identifier flag;
    public CountryData data;
    public Country(PartyTeam team){
        this.team = team;
    }

}
