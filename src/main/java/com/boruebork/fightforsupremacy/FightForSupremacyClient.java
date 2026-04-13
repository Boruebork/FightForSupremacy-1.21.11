package com.boruebork.fightforsupremacy;

import com.boruebork.fightforsupremacy.network.packets.RequestCountryDataPacket;
import com.boruebork.fightforsupremacy.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = FightForSupremacy.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = FightForSupremacy.MODID, value = Dist.CLIENT)
public class FightForSupremacyClient {
    public FightForSupremacyClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event){
        if (KeyBindings.T_MAPPING.get().consumeClick()){
            ClientPacketDistributor.sendToServer(new RequestCountryDataPacket(2));
        }
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        FightForSupremacy.LOGGER.info("HELLO FROM CLIENT SETUP");
        FightForSupremacy.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
