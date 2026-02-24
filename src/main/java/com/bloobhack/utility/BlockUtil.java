package com.bloobhack.utility;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

/**
 * Utility class for block operations
 */
public class BlockUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    
    /**
     * Check if a block can be placed at the given position
     */
    public static boolean canPlace(BlockPos pos) {
        if (mc.world == null) return false;
        
        // Check if block is within world border
        if (!mc.world.getWorldBorder().contains(pos)) return false;
        
        // Check if block is loaded
        if (!mc.world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4)) return false;
        
        // Check if block is replaceable
        return mc.world.getBlockState(pos).getMaterial().isReplaceable();
    }
    
    /**
     * Check if a block can be seen from a position (raytrace)
     */
    public static boolean canSeeBlock(net.minecraft.entity.Entity entity, BlockPos pos) {
        if (mc.world == null) return false;
        
        Vec3d eyePos = entity.getEyePos();
        Vec3d blockCenter = Vec3d.ofCenter(pos);
        
        // Simple raytrace: check if there's a direct line of sight
        return mc.world.raycast(
            new net.minecraft.util.hit.RaycastContext(
                eyePos,
                blockCenter,
                net.minecraft.util.hit.RaycastContext.ShapeType.COLLIDER,
                net.minecraft.util.hit.RaycastContext.FluidHandling.NONE,
                entity
            )
        ).getType() == net.minecraft.util.hit.HitResult.Type.MISS;
    }
    
    /**
     * Get the distance to a block
     */
    public static double getDistance(BlockPos pos) {
        if (mc.player == null) return Double.MAX_VALUE;
        return mc.player.getPos().distanceTo(Vec3d.ofCenter(pos));
    }
}
