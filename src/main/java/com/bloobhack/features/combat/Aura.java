package com.bloobhack.features.combat;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import net.minecraft.client.MinecraftClient;

/**
 * Aura Module - Basic attack aura
 */
public class Aura extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    public Aura() {
        super("Aura", "Automatically attacks nearby enemies", ModuleCategory.COMBAT);
        addSetting("range", new Setting("Range", 4.0f, SettingType.FLOAT, 1.0f, 10.0f));
        addSetting("speed", new Setting("Speed", 1, SettingType.INTEGER, 1, 20));
    }
    
    @Override
    public void onTick() {
        // Implementation placeholder
    }
}
