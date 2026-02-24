package com.bloobhack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.GameRenderer;

/**
 * Mixin for GameRenderer
 * Used for render events
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        // Render event
    }
}
