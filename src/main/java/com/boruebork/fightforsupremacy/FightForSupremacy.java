package com.boruebork.fightforsupremacy;

import com.boruebork.fightforsupremacy.client.FFSWaypoints;
import com.boruebork.fightforsupremacy.general.block.ModBlocks;
import com.boruebork.fightforsupremacy.general.block.custom.CapitalBlock;
import com.boruebork.fightforsupremacy.general.block.custom.entity.ModBE;
import com.boruebork.fightforsupremacy.general.item.ModItems;
import com.boruebork.fightforsupremacy.team.Country;
import com.boruebork.fightforsupremacy.team.CountryManager;
import com.boruebork.fightforsupremacy.team.TeamUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.FTBChunksCommands;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftbchunks.net.AddWaypointPacket;
import dev.ftb.mods.ftbchunks.net.ShareWaypointPacket;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftblibrary.util.NBTUtils;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.event.TeamManagerEvent;
import dev.ftb.mods.ftbteams.data.PartyTeam;
import dev.ftb.mods.ftbteams.data.ServerTeam;
import dev.ftb.mods.ftbteams.data.TeamManagerImpl;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBE.register(modEventBus);
        FFSWaypoints.init();
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
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS){
            event.accept(ModItems.CAPITAL_ITEM);

        }
    }
    Country israel;
    Country iran;
    MinecraftServer server;
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws CommandSyntaxException {
        server = event.getServer();
        // Do something when the server starts
        deleteTeams(event);

    // Claim a chunk

    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException {
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
    @SubscribeEvent
    public void onServerShuttingEvent(ServerStoppingEvent event){
      /*  CompoundTag tag = new CompoundTag();
        tag.putInt("capitalX", israel.capitalData.pos().getX());
        tag.putInt("capitalY", israel.capitalData.pos().getY());
        tag.putInt("capitalZ", israel.capitalData.pos().getZ());
        israel.team.getExtraData().put("capital", tag);*/
    }
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        System.err.println("EVent fired");
        if (level.getBlockState(pos).is(ModBlocks.CAPITAL)){
            for (Team team : FTBTeamsAPI.api().getManager().getTeams()) {

                boolean capitulated = team.getExtraData()
                        .getBoolean("capitulated")
                        .orElse(false);

                FTBChunksAPI.clientApi()
                        .getWaypointManager(Level.OVERWORLD)
                        .ifPresent(mgr -> mgr.removeWaypointAt(new BlockPos(team.getExtraData().getInt("capitalX").get(), team.getExtraData().getInt("capitalY").get(), team.getExtraData().getInt("capitalZ").get())));

                if (capitulated) continue;

                int x = team.getExtraData().getInt("capitalX").orElse(0);
                int y = team.getExtraData().getInt("capitalY").orElse(0);
                int z = team.getExtraData().getInt("capitalZ").orElse(0);

                BlockPos capitalPos = new BlockPos(x, y, z);

                if (pos.equals(capitalPos)) {
                    System.err.println("Match found!");

                    team.getExtraData().putBoolean("capitulated", true);

                    team.getExtraData().putInt("capitalX", 0);
                    team.getExtraData().putInt("capitalY", 0);
                    team.getExtraData().putInt("capitalZ", 0);
                    for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
                        if (team.getMembers().contains(player.getUUID())) {
                            player.sendSystemMessage(Component.literal("Your capital has been destroyed!"));
                        }
                    }

                }
            }
        }
        // find if this block is a capital
    }
    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent event){
        if (event.getState().is(ModBlocks.CAPITAL)){
            if (event.getEntity() instanceof Player player){
                Optional<Team> optionalTeam = FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID());
                if (optionalTeam.isEmpty()) return;
                Team team = optionalTeam.get();
                team.getExtraData().putInt("capitalX", event.getPos().getX());
                team.getExtraData().putInt("capitalY", event.getPos().getY());
                team.getExtraData().putInt("capitalZ", event.getPos().getZ());
                AddWaypointPacket packet = new AddWaypointPacket("capital of " + team.getName().getString(),
                        new GlobalPos(
                                Level.OVERWORLD,
                                new BlockPos(
                                        event.getPos().getX(),
                                        event.getPos().getY(),
                                        event.getPos().getZ()
                                )
                        ),
                        0xFF0000,
                        false
                        );
                for (ServerPlayer player1 : server.getPlayerList().getPlayers()) {
                    NetworkManager.sendToPlayer(player1, packet);
                }
                ((ServerPlayer) player).sendSystemMessage(Component.literal("You placed teh capital!"));
            }
        }

    }
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Optional<Team> team = FTBTeamsAPI.api().getManager().getTeamForPlayer(player);
            if (team.isEmpty()){
                return;
            }
            Team team1 = team.get();
            if (team1.getExtraData().getBoolean("capitulated").isEmpty()){
                return;
            }
            if (team1.getExtraData().getBoolean("capitulated").get()) {
                player.setGameMode(GameType.SPECTATOR);
                System.err.println("spectate");
            }
        }
    }
}
