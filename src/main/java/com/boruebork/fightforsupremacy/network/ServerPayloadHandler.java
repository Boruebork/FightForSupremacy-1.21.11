package com.boruebork.fightforsupremacy.network;

import com.boruebork.fightforsupremacy.network.packets.RequestCountryDataPacket;
import com.boruebork.fightforsupremacy.network.packets.SendDataToCountryScreenPacket;
import com.boruebork.fightforsupremacy.team.CountryManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

public class ServerPayloadHandler {
    public static void handleTeamDataRequest(RequestCountryDataPacket requestCountryDataPacket, IPayloadContext context) {
        PacketDistributor.sendToPlayer((ServerPlayer) context.player(), new SendDataToCountryScreenPacket(Objects.requireNonNull(CountryManager.findPlayerCountry(context.player())).data));
    }
}
