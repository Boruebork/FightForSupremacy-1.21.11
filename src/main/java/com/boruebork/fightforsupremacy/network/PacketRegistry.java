package com.boruebork.fightforsupremacy.network;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.network.packets.RequestCountryDataPacket;
import com.boruebork.fightforsupremacy.network.packets.SendDataToCountryScreenPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FightForSupremacy.MODID)
public class PacketRegistry {
    private static final String PROTOCOL_VERSION = "1";
    @SubscribeEvent // on the mod event bus
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.executesOn(HandlerThread.NETWORK);
        registrar.playToClient(
                SendDataToCountryScreenPacket.TYPE,
                SendDataToCountryScreenPacket.STREAM_CODEC
        );
        registrar.playToServer(
                RequestCountryDataPacket.TYPE,
                RequestCountryDataPacket.STREAM_CODEC,
                ServerPayloadHandler::handleTeamDataRequest
        );

    }
}
