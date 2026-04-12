package com.boruebork.fightforsupremacy.network.packets;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RequestCountryDataPacket(int bluff) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestCountryDataPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(FightForSupremacy.MODID, "request_country_data"));

    public static final StreamCodec<FriendlyByteBuf, RequestCountryDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RequestCountryDataPacket::bluff,
            RequestCountryDataPacket::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
