package com.boruebork.fightforsupremacy.network;

import com.boruebork.fightforsupremacy.network.packets.RequestCountryDataPacket;
import com.boruebork.fightforsupremacy.network.packets.SendDataToCountryScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleTeamDataRequest(RequestCountryDataPacket requestCountryDataPacket, IPayloadContext context) {
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new SendDataToCountryScreenPacket());
    }
}
