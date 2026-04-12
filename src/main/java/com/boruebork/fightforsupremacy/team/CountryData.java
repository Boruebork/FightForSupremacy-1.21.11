package com.boruebork.fightforsupremacy.team;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class CountryData {
    public String name;
    public CountryData(String name){
        this.name = name;
    }

    public String name() {
        return name;
    }

    public static final StreamCodec<FriendlyByteBuf, CountryData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CountryData::name,
            CountryData::new
    );
}
