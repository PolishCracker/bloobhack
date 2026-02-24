package com.bloobhack.core;

import java.util.*;

/**
 * Base class for all bloobhack modules.
 * Provides common functionality for combat, movement, render, and misc modules.
 */
public abstract class Module {
    protected String name;
    protected String description;
    protected ModuleCategory category;
    protected boolean enabled = false;
    protected Map<String, Setting> settings = new HashMap<>();
    
    public enum ModuleCategory {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        RENDER("Render"),
        MISC("Misc");
        
        public final String displayName;
        
        ModuleCategory(String displayName) {
            this.displayName = displayName;
        }
    }
    
    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    /**
     * Called when the module is enabled
     */
    public void onEnable() {
    }
    
    /**
     * Called when the module is disabled
     */
    public void onDisable() {
    }
    
    /**
     * Called every tick (20 times per second)
     */
    public void onTick() {
    }
    
    /**
     * Called on render event
     */
    public void onRender() {
    }
    
    /**
     * Enable the module
     */
    public void enable() {
        if (!enabled) {
            enabled = true;
            onEnable();
        }
    }
    
    /**
     * Disable the module
     */
    public void disable() {
        if (enabled) {
            enabled = false;
            onDisable();
        }
    }
    
    /**
     * Toggle the module
     */
    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ModuleCategory getCategory() {
        return category;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }
    
    public Map<String, Setting> getSettings() {
        return settings;
    }
    
    public void addSetting(String key, Setting setting) {
        settings.put(key, setting);
    }
    
    public Setting getSetting(String key) {
        return settings.get(key);
    }
}
