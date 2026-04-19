package com.boruebork.fightforsupremacy.mixin;

import com.boruebork.fightforsupremacy.renderer.FogOFWarRenderer;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapLayerRenderer;
import dev.ftb.mods.ftbchunks.client.minimap.MinimapRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinimapRenderer.class)
public abstract class MiniMapMixin {
    @Shadow protected abstract void addBuiltinRenderLayer(Identifier id, MinimapLayerRenderer renderer);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addFogLayer(CallbackInfo ci){
        this.addBuiltinRenderLayer(FTBChunksAPI.id("fogofwar"), FogOFWarRenderer.INTAMCE);
    }
}
