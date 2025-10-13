package com.sashafiesta.zoo.di;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DIContainer {

    private final Map<Class<?>, Class<?>> registrations = new HashMap<>();
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public DIContainer() {

    }

    public <T> void register(Class<T> type, Class<? extends T> implementation) {
        registrations.put(type, implementation);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        T instance = (T) instances.get(type);
        if (instance != null) {
            return instance;
        }
        instance = (T) instances.get(type);
        if (instance != null) {
            return instance;
        }
        try {
            Class<? extends T> implementation = getImplementation(type);
            Constructor<?> constructor = implementation.getConstructors()[0];

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getInstance(parameterTypes[i]);
            }

            instance = (T) constructor.newInstance(dependencies);

            instances.put(type, instance);
            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Class<? extends T> getImplementation(Class<T> type) {
        Class<?> implementation = registrations.get(type);
        if (implementation == null) {
            if (!type.isInterface() && !java.lang.reflect.Modifier.isAbstract(type.getModifiers())) {
                implementation = type;
            } else {
                throw new IllegalStateException("No registration found for type: " + type.getName());
            }
        }

        Class<? extends T> finalImplementation = (Class<? extends T>) implementation;
        return finalImplementation;
    }
}