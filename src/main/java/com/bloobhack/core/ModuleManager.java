package com.bloobhack.core;

import java.util.*;

import com.bloobhack.features.combat.*;
import com.bloobhack.features.movement.*;
import com.bloobhack.features.render.*;
import com.bloobhack.features.misc.*;

/**
 * Manages all modules in bloobhack.
 * Handles registration, enabling/disabling, and module lookup.
 */
public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();
    private final Map<String, Module> modulesByName = new HashMap<>();
    private final Map<Module.ModuleCategory, List<Module>> modulesByCategory = new HashMap<>();
    
    public ModuleManager() {
        // Initialize category map
        for (Module.ModuleCategory category : Module.ModuleCategory.values()) {
            modulesByCategory.put(category, new ArrayList<>());
        }
    }
    
    /**
     * Register all modules
     */
    public void registerModules() {
        // Combat modules
        registerModule(new Criticals());
        registerModule(new AutoTrap());
        registerModule(new AutoWeb());
        registerModule(new Aura());
        registerModule(new KillAura());
        registerModule(new Reach());
        
        // Movement modules
        registerModule(new Speed());
        registerModule(new Flight());
        registerModule(new NoFall());
        
        // Render modules
        registerModule(new ESP());
        registerModule(new Tracers());
        
        // Misc modules
        registerModule(new AutoRespawn());
        registerModule(new NoRender());
    }
    
    /**
     * Register a module
     */
    public void registerModule(Module module) {
        modules.add(module);
        modulesByName.put(module.getName().toLowerCase(), module);
        modulesByCategory.get(module.getCategory()).add(module);
    }
    
    /**
     * Unregister a module
     */
    public void unregisterModule(Module module) {
        modules.remove(module);
        modulesByName.remove(module.getName().toLowerCase());
        modulesByCategory.get(module.getCategory()).remove(module);
    }
    
    /**
     * Get a module by name
     */
    public Module getModule(String name) {
        return modulesByName.get(name.toLowerCase());
    }
    
    /**
     * Get all modules
     */
    public List<Module> getModules() {
        return new ArrayList<>(modules);
    }
    
    /**
     * Get modules by category
     */
    public List<Module> getModulesByCategory(Module.ModuleCategory category) {
        return new ArrayList<>(modulesByCategory.get(category));
    }
    
    /**
     * Get enabled modules
     */
    public List<Module> getEnabledModules() {
        List<Module> enabled = new ArrayList<>();
        for (Module module : modules) {
            if (module.isEnabled()) {
                enabled.add(module);
            }
        }
        return enabled;
    }
    
    /**
     * Disable all modules
     */
    public void disableAll() {
        for (Module module : modules) {
            module.disable();
        }
    }
    
    /**
     * Get total module count
     */
    public int getModuleCount() {
        return modules.size();
    }
}
