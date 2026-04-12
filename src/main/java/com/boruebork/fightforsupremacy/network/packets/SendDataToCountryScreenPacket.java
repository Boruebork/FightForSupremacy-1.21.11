package com.boruebork.fightforsupremacy.network.packets;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.team.CountryData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SendDataToCountryScreenPacket(CountryData data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendDataToCountryScreenPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(FightForSupremacy.MODID, "send_country_data_screen"));

    public static final StreamCodec<FriendlyByteBuf, SendDataToCountryScreenPacket> STREAM_CODEC = StreamCodec.composite(
            CountryData.STREAM_CODEC,
            SendDataToCountryScreenPacket::data,
            SendDataToCountryScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
