package com.bloobhack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;

/**
 * Mixin for ClientPlayerEntity
 * Used for intercepting player events and actions
 */
@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // Player tick event
    }
}
