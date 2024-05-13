import java.util.function.Function;
import java.util.Map;

public interface CustomContainer extends AutoCloseable {
    <T> boolean addInstance(T instance);
    <T> boolean addInstance(T instance, String customName);

    <T> T getInstance(Class<T> type);

    <T> T getInstance(Class<T> type, String customName);

    <T> boolean addFactoryMethod(Class<T> type, Function<CustomContainer, T> factoryMethod);

    <T> T create(Class<T> type);
    <T> T create(Class<T> type, Map<String, Object> parameters);
}
