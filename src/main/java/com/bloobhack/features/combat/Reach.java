package com.bloobhack.features.combat;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import net.minecraft.client.MinecraftClient;

/**
 * Reach Module - Increases attack reach distance
 */
public class Reach extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    public Reach() {
        super("Reach", "Increases attack reach distance", ModuleCategory.COMBAT);
        addSetting("distance", new Setting("Distance", 3.5f, SettingType.FLOAT, 3.0f, 10.0f));
    }
    
    @Override
    public void onTick() {
        // Implementation placeholder
    }
}
