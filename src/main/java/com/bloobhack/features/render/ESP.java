package com.bloobhack.features.render;

import com.bloobhack.core.Module;
import com.bloobhack.core.Setting;
import com.bloobhack.core.Setting.SettingType;
import net.minecraft.client.MinecraftClient;

/**
 * ESP Module - Renders boxes around enemies
 */
public class ESP extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    public ESP() {
        super("ESP", "Renders boxes around enemies", ModuleCategory.RENDER);
        addSetting("range", new Setting("Range", 100.0f, SettingType.FLOAT, 10.0f, 500.0f));
        addSetting("boxColor", new Setting("Color", "Red", SettingType.STRING));
    }
    
    @Override
    public void onRender() {
        // Implementation placeholder
    }
}
