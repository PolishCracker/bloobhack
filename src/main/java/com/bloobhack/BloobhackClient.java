package com.bloobhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bloobhack.core.ModuleManager;
import com.bloobhack.core.EventBus;
import com.bloobhack.gui.BloobhackGui;

/**
 * bloobhack - Educational Minecraft Client for Fabric 1.21.4
 * Author: Pogracz
 * 
 * Main entry point for the bloobhack client.
 * Initializes core systems: EventBus, ModuleManager, and GUI.
 */
@Environment(EnvType.CLIENT)
public class BloobhackClient implements ClientModInitializer {
    public static final String MOD_ID = "bloobhack";
    public static final String MOD_NAME = "bloobhack";
    public static final String MOD_VERSION = "1.0.0";
    
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    
    public static EventBus EVENT_BUS;
    public static ModuleManager MODULE_MANAGER;
    public static BloobhackGui GUI;
    
    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing bloobhack v{}", MOD_VERSION);
        
        // Initialize core systems
        EVENT_BUS = new EventBus();
        MODULE_MANAGER = new ModuleManager();
        GUI = new BloobhackGui();
        
        // Register modules
        MODULE_MANAGER.registerModules();
        
        LOGGER.info("bloobhack initialized successfully!");
    }
}
