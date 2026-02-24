package com.bloobhack.features.combat;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/**
 * Criticals Module - Forces critical hits on enemies
 * 
 * Grim v3 Bypass Strategy:
 * - Sends minimal Y-axis offset packets to bypass fall damage detection
 * - Uses precise timing to avoid detection
 * - Supports multiple modes: Packet, Jump, Strict
 */
public class Criticals extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    public enum CriticalMode {
        PACKET("Packet"),
        JUMP("Jump"),
        STRICT("Strict"),
        GRIM_V3("Grim v3");
        
        public final String displayName;
        
        CriticalMode(String displayName) {
            this.displayName = displayName;
        }
    }
    
    private CriticalMode mode = CriticalMode.GRIM_V3;
    private int tickCounter = 0;
    
    public Criticals() {
        super("Criticals", "Forces critical hits on enemies", ModuleCategory.COMBAT);
        
        // Add settings
        addSetting("mode", new Setting("Mode", CriticalMode.GRIM_V3, SettingType.ENUM));
        addSetting("delay", new Setting("Delay", 0, SettingType.INTEGER, 0, 20));
    }
    
    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        
        tickCounter++;
        int delay = ((Setting) getSetting("delay")).getAsInt();
        
        if (tickCounter < delay) return;
        tickCounter = 0;
        
        switch (mode) {
            case PACKET -> doPacketCrit();
            case JUMP -> doJumpCrit();
            case STRICT -> doStrictCrit();
            case GRIM_V3 -> doGrimV3Crit();
        }
    }
    
    /**
     * Standard packet-based critical hit
     */
    private void doPacketCrit() {
        if (mc.player.isOnGround()) {
            sendCritPacket(0.0625, false);
            sendCritPacket(0.0, false);
        }
    }
    
    /**
     * Jump-based critical hit
     */
    private void doJumpCrit() {
        if (mc.player.isOnGround()) {
            mc.player.jump();
        }
    }
    
    /**
     * Strict critical hit with multiple packets
     */
    private void doStrictCrit() {
        if (mc.player.isOnGround()) {
            sendCritPacket(0.0625, false);
            sendCritPacket(0.0625, false);
            sendCritPacket(0.0, false);
        }
    }
    
    /**
     * Grim v3 Bypass - Uses minimal Y offset to avoid detection
     * Grim v3 checks for fall damage flag and velocity changes
     * This bypass sends packets with very small Y changes
     */
    private void doGrimV3Crit() {
        if (mc.player.isOnGround()) {
            // Send packet with minimal offset (0.000001)
            sendCritPacket(0.000001, false);
            // Send packet with 0 offset to reset
            sendCritPacket(0.0, false);
        }
    }
    
    /**
     * Send a critical hit packet
     * @param yOffset Y-axis offset for the packet
     * @param onGround Whether player is on ground
     */
    private void sendCritPacket(double yOffset, boolean onGround) {
        if (mc.player == null) return;
        
        double x = mc.player.getX();
        double y = mc.player.getY() + yOffset;
        double z = mc.player.getZ();
        float yaw = mc.player.getYaw();
        float pitch = mc.player.getPitch();
        
        PlayerMoveC2SPacket.PositionAndOnGround packet = 
            new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround);
        
        mc.getNetworkHandler().sendPacket(packet);
    }
    
    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendMessage(
                net.minecraft.text.Text.literal("§6[bloobhack] §aCriticals enabled"), 
                true
            );
        }
    }
    
    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.sendMessage(
                net.minecraft.text.Text.literal("§6[bloobhack] §cCriticals disabled"), 
                true
            );
        }
    }
}
