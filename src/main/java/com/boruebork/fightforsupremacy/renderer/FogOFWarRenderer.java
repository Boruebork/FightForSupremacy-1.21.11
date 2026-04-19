package com.boruebork.fightforsupremacy.renderer;

import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapLayerRenderer;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapRenderContext;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix3x2fStack;

public enum FogOFWarRenderer implements MinimapLayerRenderer {
    INTAMCE;

    private FogOFWarRenderer() {
    }

    @Override
    public void renderLayer(GuiGraphics guiGraphics, Matrix3x2fStack poseStack, MinimapRenderContext ctx) {
        int size = ctx.size();

        // full dark overlay inside minimap space
        guiGraphics.fill(
                -size / 2,
                -size / 2,
                size / 2,
                size / 2,
                0xAA000000
        );
    }
}
