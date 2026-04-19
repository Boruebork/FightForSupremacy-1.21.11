package com.boruebork.fightforsupremacy.mixin;

import dev.ftb.mods.ftbchunks.client.mapicon.EntityIconUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityIconUtils.class)
public class FTBChunksClientMixin {
    /*@Inject(method = "onMapIconEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void filterEntities(MapIconEvent event, CallbackInfo ci){
        Minecraft mc = Minecraft.getInstance();
        Vec3 pos = mc.player.position();
        for (Entity entity : mc.level.entitiesForRendering())

    }*/
    @Inject(method = "shouldEntityRender",
            at = @At("HEAD"),
            cancellable = true)
    private static void checkDistance(Entity entity, Player player, CallbackInfoReturnable<Boolean> cir){
        if (entity.level() != player.level()) {
            cir.setReturnValue(false);
            return;
        }
        if (entity.distanceTo(player) > 5){
            cir.setReturnValue(false);
        }
        //TODO check if other entity is a PLAYER on your team
    }
}
