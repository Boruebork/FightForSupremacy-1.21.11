package com.boruebork.fightforsupremacy.general.item;

import com.boruebork.fightforsupremacy.FightForSupremacy;
import com.boruebork.fightforsupremacy.general.block.ModBlocks;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FightForSupremacy.MODID);

    public static final DeferredItem<BlockItem> CAPITAL_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.CAPITAL);

    public static void register(IEventBus b){
        ITEMS.register(b);
    }
    //public static final DeferredItem<Item> BANNER = ITEMS.registerItem("israel", (properties -> new BannerItem()))
}
