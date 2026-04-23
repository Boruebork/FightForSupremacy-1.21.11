package com.boruebork.fightforsupremacy.general.entity.custom.client.radar;

import com.boruebork.fightforsupremacy.general.entity.custom.RadarEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class RadarRenderer extends EntityRenderer<RadarEntity, RadarRenderState> {
    public RadarRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public RadarRenderState createRenderState() {
        return new RadarRenderState();
    }
}
