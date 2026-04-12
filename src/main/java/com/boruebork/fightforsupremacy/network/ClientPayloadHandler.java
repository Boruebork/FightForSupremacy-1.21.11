package com.boruebork.fightforsupremacy.network;

import com.boruebork.fightforsupremacy.CountryScreen;
import com.boruebork.fightforsupremacy.network.packets.SendDataToCountryScreenPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    //Client
    public static void sendTeamDataToScreen(SendDataToCountryScreenPacket sendDataToCountryScreenPacket, IPayloadContext context) {
        if (Minecraft.getInstance().screen instanceof CountryScreen screen){
            screen.data = sendDataToCountryScreenPacket.data();
        }else{
            CountryScreen screen = new CountryScreen();
            Minecraft.getInstance().setScreen(screen);
            screen.data = sendDataToCountryScreenPacket.data();
        }
    }
}
