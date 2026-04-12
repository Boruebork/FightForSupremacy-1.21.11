package com.boruebork.fightforsupremacy.network;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.network.packets.SendDataToCountryScreenPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

@EventBusSubscriber(modid = FightForSupremacy.MODID, value = Dist.CLIENT)
public class ClientPacketRegistry {
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void register(RegisterClientPayloadHandlersEvent event) {
        event.register(
                SendDataToCountryScreenPacket.TYPE,
                HandlerThread.NETWORK,
                ClientPayloadHandler::sendTeamDataToScreen
        );
    }
}
