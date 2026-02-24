package com.bloobhack.features.movement;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import net.minecraft.client.MinecraftClient;

/**
 * Flight Module - Allows flying
 */
public class Flight extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    public Flight() {
        super("Flight", "Allows flying", ModuleCategory.MOVEMENT);
        addSetting("speed", new Setting("Speed", 1.0f, SettingType.FLOAT, 0.1f, 5.0f));
    }
    
    @Override
    public void onTick() {
        // Implementation placeholder
    }
}
