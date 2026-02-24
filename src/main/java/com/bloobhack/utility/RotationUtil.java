package com.bloobhack.utility;

import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;

/**
 * Utility class for rotation calculations and silent rotations
 */
public class RotationUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    
    // Silent rotation state (not sent to server)
    private static float silentYaw = 0;
    private static float silentPitch = 0;
    
    /**
     * Calculate rotation angles to look at a target position
     */
    public static float[] getRotation(Vec3d from, Vec3d to) {
        Vec3d diff = to.subtract(from);
        
        double distance = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        
        float yaw = (float) Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90;
        float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, distance));
        
        return new float[] { yaw, pitch };
    }
    
    /**
     * Apply silent rotation (client-side only, not sent to server)
     * Used for bypassing anticheat rotation detection
     */
    public static void applySilentRotation(float yaw, float pitch) {
        silentYaw = yaw;
        silentPitch = pitch;
    }
    
    /**
     * Get silent yaw
     */
    public static float getSilentYaw() {
        return silentYaw;
    }
    
    /**
     * Get silent pitch
     */
    public static float getSilentPitch() {
        return silentPitch;
    }
    
    /**
     * Normalize angle to -180 to 180
     */
    public static float normalizeAngle(float angle) {
        while (angle >= 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
    
    /**
     * Calculate distance between two angles
     */
    public static float getAngleDifference(float angle1, float angle2) {
        float diff = normalizeAngle(angle1 - angle2);
        return Math.abs(diff);
    }
}
