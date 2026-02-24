package com.bloobhack.core;

/**
 * Represents a setting for a module.
 * Supports various types: boolean, integer, float, string, enum.
 */
public class Setting {
    private String name;
    private Object value;
    private Object defaultValue;
    private SettingType type;
    private Object minValue;
    private Object maxValue;
    
    public enum SettingType {
        BOOLEAN,
        INTEGER,
        FLOAT,
        STRING,
        ENUM
    }
    
    public Setting(String name, Object defaultValue, SettingType type) {
        this.name = name;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.type = type;
    }
    
    public Setting(String name, Object defaultValue, SettingType type, Object minValue, Object maxValue) {
        this(name, defaultValue, type);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    public String getName() {
        return name;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        // Validate range if applicable
        if (minValue != null && maxValue != null) {
            if (type == SettingType.INTEGER) {
                int intVal = (int) value;
                int min = (int) minValue;
                int max = (int) maxValue;
                value = Math.max(min, Math.min(max, intVal));
            } else if (type == SettingType.FLOAT) {
                float floatVal = (float) value;
                float min = (float) minValue;
                float max = (float) maxValue;
                value = Math.max(min, Math.min(max, floatVal));
            }
        }
        this.value = value;
    }
    
    public Object getDefaultValue() {
        return defaultValue;
    }
    
    public void resetToDefault() {
        this.value = defaultValue;
    }
    
    public SettingType getType() {
        return type;
    }
    
    public Object getMinValue() {
        return minValue;
    }
    
    public Object getMaxValue() {
        return maxValue;
    }
    
    public boolean getAsBoolean() {
        return (boolean) value;
    }
    
    public int getAsInt() {
        return (int) value;
    }
    
    public float getAsFloat() {
        return (float) value;
    }
    
    public String getAsString() {
        return (String) value;
    }
}
