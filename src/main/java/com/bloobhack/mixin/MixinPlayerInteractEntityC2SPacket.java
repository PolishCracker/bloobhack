package com.bloobhack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

/**
 * Mixin for PlayerInteractEntityC2SPacket
 * Used for intercepting entity interaction packets
 */
@Mixin(PlayerInteractEntityC2SPacket.class)
public class MixinPlayerInteractEntityC2SPacket {
    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        // Entity interaction packet intercepted
    }
}
