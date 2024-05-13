import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Tests extends TaskTestsBase {

    @ParameterizedTest
    @MethodSource("testGetInstanceByTypeData")
    <T> void testGetInstanceByType(T obj) {
        customContainer.addInstance(obj);

        T actual = (T) customContainer.getInstance(obj.getClass());
        assertEquals(obj, actual, "Retrieved objects should be equal");
    }

    @ParameterizedTest
    @MethodSource("testGetInstanceByTypeData")
    <T> void testGetInstanceByTypeAndName(T obj) {
        String name = "instanceName";
        customContainer.addInstance(obj, name);

        T actual = (T) customContainer.getInstance(obj.getClass(), name);
        assertEquals(obj, actual, "Retrieved objects should be equal");
    }

    @Test
    void testGetInstanceExceptionWhenNullType() {
        assertParameterNotNull(() -> customContainer.getInstance(null));
        assertParameterNotNull(() -> customContainer.getInstance(null, "name"));
    }

    @Test
    void testGetInstanceExceptionWhenNullName() {
        assertParameterNotNull(() -> customContainer.getInstance(Object.class, null));
    }

    static Stream<Arguments> testGetInstanceByTypeData() {
        return Stream.of(
                Arguments.of(
                        new Object()),
                Arguments.of(5),
                Arguments.of(false),
                Arguments.of(new SimplePOJO(0, "asd"))
        );
    }
}