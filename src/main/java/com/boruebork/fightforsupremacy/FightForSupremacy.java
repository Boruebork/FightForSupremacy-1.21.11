package com.boruebork.fightforsupremacy;

import com.boruebork.fightforsupremacy.team.Country;
import com.boruebork.fightforsupremacy.team.CountryManager;
import com.boruebork.fightforsupremacy.team.TeamUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.data.PartyTeam;
import dev.ftb.mods.ftbteams.data.ServerTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.UUID;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FightForSupremacy.MODID)
public class FightForSupremacy {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fightforsupremacy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "fightforsupremacy" namespace
    // Creates a creative tab with the id "fightforsupremacy:example_tab" for the example item, that is placed after the combat tab
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FightForSupremacy(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (FightForSupremacy) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }
    Country israel;
    Country iran;
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws CommandSyntaxException {
        // Do something when the server starts
        deleteTeams(event);
        israel = CountryManager.createCountry(event.getServer(), "Israel","Israel a country", Color4I.BLUE);
        //israel =  new Country((ServerTeam) ((TeamManagerImpl) FTBTeamsAPI.api().getManager()).createServerTeam(TeamUtil.fakeSource(event.getServer()), "Israel", "isr", Color4I.BLUE, UUID.randomUUID()));
        //iran = new Country((TeamManagerImpl) FTBTeamsAPI.api().getManager(), UUID.randomUUID());
        ClaimedChunkManager manager = FTBChunksAPI.api().getManager();
        // Get team data for a player
        ChunkTeamData teamData = manager.getOrCreateData(israel.team);

// Claim a chunk
        ChunkDimPos pos = new ChunkDimPos(Objects.requireNonNull(event.getServer().getLevel(Level.OVERWORLD)), new BlockPos(2387, 0, -2178));
        teamData.claim(TeamUtil.fakeSource(event.getServer()), pos, false);
    }
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        //FTBTeamsAPI.api().getManager().
        israel.team.addMember(event.getEntity().getUUID(), TeamRank.MEMBER);
        israel.team.markDirty();
        TeamManagerImpl manager = (TeamManagerImpl) FTBTeamsAPI.api().getManager();
        manager.syncToAll(israel.team);
        LOGGER.info("player " + event.getEntity().getName().getString() + " joined Israel!");
        System.err.println("sss");
    }

    public void deleteTeams(ServerStartingEvent event) {
        TeamManagerImpl manager = (TeamManagerImpl) FTBTeamsAPI.api().getManager();
        for (Team team :  manager.getTeams()){
            if (!(team instanceof ServerTeam serverTeam)) return;
            try {
                serverTeam.delete(event.getServer().createCommandSourceStack());
            } catch (Exception e) {
                FTBTeams.LOGGER.error("Failed to delete server team: " + serverTeam.getName(), e);
            }
        }
        // Get all server teams and delete them
    }
}
