package com.boruebork.fightforsupremacy.focus;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Focus {
    public int timeInTicks;
    public List<ItemStack> resourcesNeeded;
    public String name;
    public String description;
    public Identifier texture;
    //TODO some Functional Interface method for completed/started focus
}
