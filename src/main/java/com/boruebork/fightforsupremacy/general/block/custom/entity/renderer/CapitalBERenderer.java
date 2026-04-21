package com.boruebork.fightforsupremacy.general.block.custom.entity.renderer;

import com.boruebork.fightforsupremacy.general.block.custom.entity.CapitalBLockEntity;
import com.boruebork.fightforsupremacy.util.Colors;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CapitalBERenderer implements BlockEntityRenderer<CapitalBLockEntity, CapitalBERenderState> {
    public CapitalBERenderer(BlockEntityRendererProvider.Context context) {
        //itemModelResolver = context.itemModelResolver();
    }
    @Override
    public CapitalBERenderState createRenderState() {
        return new CapitalBERenderState();
    }
    @Override
    public void extractRenderState(CapitalBLockEntity blockEntity, CapitalBERenderState renderState, float partialTick,
                                   Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        /*renderState.lightPosition = blockEntity.getBlockPos();
        renderState.blockEntityLevel = blockEntity.getLevel();
        renderState.rotation = blockEntity.getRenderingRotation();*/
        renderState.teamName = blockEntity.teamName;

       /* itemModelResolver.updateForTopItem(renderState.itemStackRenderState,
                blockEntity.inventory.getStackInSlot(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);*/
    }


    @Override
    public void submit(CapitalBERenderState capitalBERenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.2, 0.5);
        submitNodeCollector.submitText(poseStack, 0, 0, Component.literal(capitalBERenderState.teamName).getVisualOrderText(), false, Font.DisplayMode.NORMAL, Colors.GREEN, Colors.BLACK, capitalBERenderState.lightCoords, 0);
        poseStack.translate(0.5, 1.2, 0.5);

    }
}
