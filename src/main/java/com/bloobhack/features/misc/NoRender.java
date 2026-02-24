package com.bloobhack.features.misc;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import net.minecraft.client.MinecraftClient;

/**
 * NoRender Module - Disables rendering of certain elements
 */
public class NoRender extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    public NoRender() {
        super("NoRender", "Disables rendering of certain elements", ModuleCategory.MISC);
        addSetting("fog", new Setting("Fog", true, SettingType.BOOLEAN));
        addSetting("fire", new Setting("Fire", false, SettingType.BOOLEAN));
    }
    
    @Override
    public void onRender() {
        // Implementation placeholder
    }
}
