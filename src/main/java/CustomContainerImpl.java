import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomContainerImpl implements CustomContainer {

    private Map<String, Object> instances = new HashMap<>();
    private Map<String, Function<Map<String, Object>, Object>> factoryMethods = new HashMap<>();
    @Override
    public <T> boolean addInstance(T instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Null is not allowed as a parameter");
        }
        String instance_name = instance.getClass().getName();
        return addInstance(instance,instance_name);
    }

    @Override
    public <T> boolean addInstance(T instance, String customName) {
        if (instance == null || customName == null) {
            throw new IllegalArgumentException("Null is not allowed as a parameter");
        }
        if (instances.containsKey(customName)) {
            throw new IllegalStateException("Instances cannot be redeclared");
        }
        instances.put(customName, instance);
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Null is not allowed as a parameter");
        }

        Object instance = instances.get(type.getName());
        if (instance != null) {
            if (!type.isInstance(instance)) {
                throw new IllegalStateException("Invalid type for object");
            }
            return type.cast(instance);
        }

        Function<Map<String, Object>, Object> factoryMethod = factoryMethods.get(type.getName());
        if (factoryMethod != null) {
            instance = factoryMethod.apply(instances);
            instances.put(type.getName(), instance);
            return type.cast(instance);
        }

        throw new IllegalStateException("Cannot provide instance");
    }

    @Override
    public <T> T getInstance(Class<T> type, String instance_name) {
        if (type == null ||instance_name == null) {
            throw new IllegalArgumentException("Null is not allowed as a parameter");
        }
        Object instance = instances.get(instance_name);
        if (instance != null) {
            if (!type.isInstance(instance)) {
                throw new IllegalStateException("Invalid type for object");
            }
            return type.cast(instance);
        }
        Function<Map<String, Object>, Object> factoryMethod = factoryMethods.get(type.getName());
        if (factoryMethod != null) {
            instance = factoryMethod.apply(instances);
            instances.put(type.getName(), instance);
            return type.cast(instance);
        }
        throw new IllegalStateException("Cannot provide instance");
    }

    @Override
    public <T> boolean addFactoryMethod(Class<T> type, Function<CustomContainer, T> factoryMethod) {
        if (type == null || factoryMethod == null) {
            throw new IllegalArgumentException("Null is not allowed as a parameter");
        }
        return factoryMethods.put(type.getName(), container -> factoryMethod.apply(this)) == null;
    }


    @Override
    public <T> T create(Class<T> type) {
        return null;
    }

    @Override
    public <T> T create(Class<T> type, Map<String, Object> parameters) {
        return null;
    }

    @Override
    public void close() {
        for (Object instance : instances.values()) {
            if (instance instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) instance).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

