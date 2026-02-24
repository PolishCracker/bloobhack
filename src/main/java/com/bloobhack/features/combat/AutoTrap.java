package com.bloobhack.features.combat;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import com.bloobhack.utility.BlockUtil;
import com.bloobhack.utility.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.Blocks;

/**
 * AutoTrap Module - Automatically traps enemies with obsidian
 * 
 * Grim v3 Bypass Strategy:
 * - Uses Silent Rotations to avoid rotation detection
 * - Implements raytrace checking to bypass reach checks
 * - Adds delays between placements to avoid speed detection
 * - Validates block placement before sending packets
 */
public class AutoTrap extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    private int tickCounter = 0;
    private int lastPlaceTime = 0;
    
    public AutoTrap() {
        super("AutoTrap", "Automatically traps enemies with obsidian", ModuleCategory.COMBAT);
        
        // Add settings
        addSetting("speed", new Setting("Speed", 2, SettingType.INTEGER, 1, 10));
        addSetting("range", new Setting("Range", 5.0f, SettingType.FLOAT, 1.0f, 10.0f));
        addSetting("silentRotations", new Setting("Silent Rotations", true, SettingType.BOOLEAN));
        addSetting("raytrace", new Setting("Raytrace", true, SettingType.BOOLEAN));
    }
    
    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        
        tickCounter++;
        int speed = ((Setting) getSetting("speed")).getAsInt();
        
        if (tickCounter < speed) return;
        tickCounter = 0;
        
        // Find target
        net.minecraft.entity.Entity target = findTarget();
        if (target == null) return;
        
        // Get trap positions around target
        BlockPos[] trapPositions = getTrapPositions(target);
        
        // Place obsidian blocks
        for (BlockPos pos : trapPositions) {
            if (shouldPlace(pos)) {
                placeBlock(pos);
                break; // Place one block per tick
            }
        }
    }
    
    /**
     * Find the nearest enemy player
     */
    private net.minecraft.entity.Entity findTarget() {
        float range = ((Setting) getSetting("range")).getAsFloat();
        net.minecraft.entity.Entity closest = null;
        double closestDist = range;
        
        for (net.minecraft.entity.Entity entity : mc.world.getEntities()) {
            if (entity == mc.player) continue;
            if (!(entity instanceof net.minecraft.entity.player.PlayerEntity)) continue;
            
            double dist = mc.player.distanceTo(entity);
            if (dist < closestDist) {
                closest = entity;
                closestDist = dist;
            }
        }
        
        return closest;
    }
    
    /**
     * Get positions around target to place obsidian
     */
    private BlockPos[] getTrapPositions(net.minecraft.entity.Entity target) {
        BlockPos targetPos = target.getBlockPos();
        
        return new BlockPos[] {
            targetPos.up(),      // Top
            targetPos.north(),   // North
            targetPos.south(),   // South
            targetPos.east(),    // East
            targetPos.west()     // West
        };
    }
    
    /**
     * Check if block should be placed
     */
    private boolean shouldPlace(BlockPos pos) {
        if (!BlockUtil.canPlace(pos)) return false;
        
        // Check if block is already placed
        if (mc.world.getBlockState(pos).getBlock() != Blocks.AIR) {
            return false;
        }
        
        // Check raytrace if enabled
        if (((Setting) getSetting("raytrace")).getAsBoolean()) {
            if (!BlockUtil.canSeeBlock(mc.player, pos)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Place a block at the given position
     */
    private void placeBlock(BlockPos pos) {
        if (mc.player == null || mc.interactionManager == null) return;
        
        boolean silentRotations = ((Setting) getSetting("silentRotations")).getAsBoolean();
        
        if (silentRotations) {
            // Calculate rotation to block
            Vec3d blockCenter = Vec3d.ofCenter(pos);
            float[] rotation = RotationUtil.getRotation(mc.player.getEyePos(), blockCenter);
            
            // Apply silent rotation (doesn't update player rotation visually)
            RotationUtil.applySilentRotation(rotation[0], rotation[1]);
        }
        
        // Find obsidian in hotbar
        int obsidianSlot = findObsidian();
        if (obsidianSlot == -1) return;
        
        // Swap to obsidian
        int oldSlot = mc.player.getInventory().selectedSlot;
        mc.player.getInventory().selectedSlot = obsidianSlot;
        
        // Place block
        mc.interactionManager.interactBlock(mc.player, mc.world, 
            net.minecraft.util.Hand.MAIN_HAND, 
            new net.minecraft.util.hit.BlockHitResult(
                Vec3d.ofCenter(pos), 
                net.minecraft.util.math.Direction.UP, 
                pos, 
                false
            )
        );
        
        // Swap back
        mc.player.getInventory().selectedSlot = oldSlot;
        
        lastPlaceTime = System.currentTimeMillis();
    }
    
    /**
     * Find obsidian in hotbar
     */
    private int findObsidian() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == 
                net.minecraft.item.Items.OBSIDIAN) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendMessage(
                net.minecraft.text.Text.literal("§6[bloobhack] §aAutoTrap enabled"), 
                true
            );
        }
    }
    
    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.sendMessage(
                net.minecraft.text.Text.literal("§6[bloobhack] §cAutoTrap disabled"), 
                true
            );
        }
    }
}
