package com.bloobhack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;

/**
 * Mixin for Screen
 * Used for GUI events
 */
@Mixin(Screen.class)
public class MixinScreen {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Screen render event
    }
}
