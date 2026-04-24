package com.boruebork.fightforsupremacy.client.mapicon;

import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftblibrary.client.gui.input.Key;
import dev.ftb.mods.ftblibrary.client.gui.input.MouseButton;
import dev.ftb.mods.ftblibrary.client.gui.widget.BaseScreen;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MapIconEx implements MapIcon {
    @Override
    public Vec3 getPos(float partialTick) {
        Player p = Minecraft.getInstance().player;
        if (partialTick >= 1f) {
            assert p != null;
            return p.position();
        } else {
            assert p != null;
            return p.getPosition(partialTick);
        }
    }

    @Override
    public boolean onMousePressed(BaseScreen baseScreen, MouseButton mouseButton) {
        return false;
    }

    @Override
    public boolean onKeyPressed(BaseScreen baseScreen, Key key) {
        return false;
    }

    @Override
    public double getIconScale(MapType mapType) {
        return 99900;
    }

    @Override
    public void draw(MapType mapType, GuiGraphics guiGraphics, int x, int y, int w, int h, boolean b, int i4) {
        int cx = x + w / 2;
        int cy = y + h / 2;
        int steps = 16;
        for (int i = steps; i > 0; i--) {
            int a = (int)(180 * (i / (float) steps));
            int rx = w / 2 * i / steps;
            int ry = h / 2 * i / steps;
            Color4I.BLACK.withAlpha(a / steps).draw(guiGraphics, cx - rx, cy - ry, rx * 2, ry * 2);
        }
    }
}