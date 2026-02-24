package com.bloobhack.core;

import java.lang.reflect.Method;
import java.util.*;

/**
 * EventBus system for bloobhack.
 * Allows modules to subscribe and listen to game events.
 */
public class EventBus {
    private final Map<Class<?>, List<EventListener>> listeners = new HashMap<>();
    
    public static class EventListener {
        public final Object instance;
        public final Method method;
        public final int priority;
        
        public EventListener(Object instance, Method method, int priority) {
            this.instance = instance;
            this.method = method;
            this.priority = priority;
        }
    }
    
    /**
     * Subscribe to an event
     */
    public void subscribe(Class<?> eventClass, Object listener, Method method, int priority) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(
            new EventListener(listener, method, priority)
        );
        
        // Sort by priority (higher priority first)
        listeners.get(eventClass).sort((a, b) -> Integer.compare(b.priority, a.priority));
    }
    
    /**
     * Unsubscribe from an event
     */
    public void unsubscribe(Class<?> eventClass, Object listener) {
        List<EventListener> eventListeners = listeners.get(eventClass);
        if (eventListeners != null) {
            eventListeners.removeIf(l -> l.instance == listener);
        }
    }
    
    /**
     * Post an event to all listeners
     */
    public void post(Object event) {
        List<EventListener> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                try {
                    listener.method.invoke(listener.instance, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Clear all listeners
     */
    public void clear() {
        listeners.clear();
    }
}
