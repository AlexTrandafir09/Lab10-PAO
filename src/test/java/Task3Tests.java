import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Tests extends TaskTestsBase {
    @Test
    void testAddFactoryMethod() {
        assertTrue(
                customContainer.addFactoryMethod(Object.class, (container) -> new Object()),
                "Should return true"
        );
    }

    @Test
    void testAddFactoryMethodShouldThrowWhenNullParameters() {
        assertParameterNotNull(() -> customContainer.addFactoryMethod(Object.class, null));
        assertParameterNotNull(() -> customContainer.addFactoryMethod(null, (container) -> null));
    }
}