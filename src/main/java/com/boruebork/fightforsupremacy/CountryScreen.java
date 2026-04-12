package com.boruebork.fightforsupremacy;

import com.boruebork.fightforsupremacy.team.CountryData;
import com.boruebork.fightforsupremacy.util.Colors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CountryScreen extends Screen {
    public CountryData data;
    public CountryScreen() {
        super(Component.literal("Country Screen"));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(0, 0, width, height, Colors.DARK_GRAY);
        guiGraphics.drawString(this.font, data.name,0,0, Colors.GREEN);

    }
}
