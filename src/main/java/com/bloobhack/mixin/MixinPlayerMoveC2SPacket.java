package com.bloobhack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/**
 * Mixin for PlayerMoveC2SPacket
 * Used for intercepting and modifying movement packets
 */
@Mixin(PlayerMoveC2SPacket.class)
public class MixinPlayerMoveC2SPacket {
    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        // Packet intercepted for potential modifications
    }
}
