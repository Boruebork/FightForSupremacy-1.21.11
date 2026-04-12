package com.boruebork.fightforsupremacy.focus;

import com.boruebork.fightforsupremacy.util.Colors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Set;

public class FocusScreen extends Screen {
    public Set<Integer> finishedFocuses;
    private int x;
    //public FocusDataMenu startFocusMenu = null;
    private int y;
    public int currentFocus = -1;
    public int timeUntilEndOfFocus;
    //private List<FocusData> focusData;
    //private List<FocusButton> focusButtons;
    public FocusScreen() {
        super(Component.literal("Focus Screen"));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(0, 0, this.width, this.height, Colors.DARK_GRAY);
    }
}
