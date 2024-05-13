import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Tests extends TaskTestsBase {

    @Test
    void testGetInstanceWithFactoryMethod() {
        final SimplePOJO expected = new SimplePOJO(1, "asd");
        customContainer.addFactoryMethod(SimplePOJO.class, (container) -> expected);

        SimplePOJO actual = customContainer.getInstance(SimplePOJO.class);
        assertEquals(expected, actual, "Should be the same SimplePOJO");
    }

    @Test
    void testGetInstanceWithNestedDependencies() {
        final SimplePOJO expected = new SimplePOJO(1, "asd");

        customContainer.addInstance(1);
        customContainer.addInstance("asd");
        customContainer.addFactoryMethod(SimplePOJO.class, (container) -> {
            int number = container.getInstance(Integer.class);
            String string = container.getInstance(String.class);
            return new SimplePOJO(number, string);
        });

        SimplePOJO actual = customContainer.getInstance(SimplePOJO.class);
        assertEquals(expected, actual, "Should be the same SimplePOJO");
    }

    @Test
    void testGetInstanceWithFactoryMethodWithMultipleCalls() {
        customContainer.addFactoryMethod(Object.class, (container) -> new Object());

        Object first = customContainer.getInstance(Object.class);
        Object second = customContainer.getInstance(Object.class);
        assertEquals(first, second);
    }

    @Test
    void testGetInstanceWithNoInstanceFoundShouldThrow() {
        assertExceptionMessage(() -> customContainer.getInstance(Object.class), "Cannot provide instance");
    }
}