import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Tests extends TaskTestsBase {
    @ParameterizedTest
    @MethodSource("testAddInstanceReturnsTrueData")
    void testAddInstanceReturnsTrue(List<Object> objectsToAdd) {
        objectsToAdd.forEach(objToAdd -> {
            boolean actual = customContainer.addInstance(objToAdd);
            assertTrue(actual, "AddInstance returns true if successful");
        });
    }

    @Test
    void testAddInstanceMultipleOfSameTypeWithDifferentNameReturnsTrue() {
        {
            Object objectToAdd = new SimplePOJO(1, "asd");
            boolean actual = customContainer.addInstance(objectToAdd, "name1");
            assertTrue(actual, "AddInstance returns true if successful");
        }
        {
            Object objectToAdd = new SimplePOJO(1, "asd");
            boolean actual = customContainer.addInstance(objectToAdd, "name2");
            assertTrue(actual, "AddInstance returns true if successful");
        }
        {
            Object objectToAdd = new SimplePOJO(1, "asd");
            boolean actual = customContainer.addInstance(objectToAdd);
            assertTrue(actual, "AddInstance returns true if successful");
        }
    }

    @Test
    void testAddInstanceThrowsExceptionOnDuplicate() {
        Object objToAdd = new Object();
        boolean actual = customContainer.addInstance(objToAdd);
        assertTrue(actual, "AddInstance returns true if successful");

        assertExceptionMessage(() -> customContainer.addInstance(objToAdd), "Instances cannot be redeclared");

    }

    @Test
    void testAddInstanceThrowsExceptionOnDifferentObjectsOfSameClass() {
        final Object objToAdd = 6;
        boolean actual = customContainer.addInstance(objToAdd);
        assertTrue(actual, "AddInstance returns true if successful");

        final Object objToAdd2 = 5;
        assertExceptionMessage(() -> customContainer.addInstance(objToAdd2), "Instances cannot be redeclared");
    }

    @Test
    void testAddInstanceThrowsExceptionOnDifferentObjects() {
        final Object objToAdd = 6;
        boolean actual = customContainer.addInstance(objToAdd, "customName");
        assertTrue(actual, "AddInstance returns true if successful");

        final Object objToAdd2 = new Object();
        assertExceptionMessage(() -> customContainer.addInstance(objToAdd2, "customName"), "Instances cannot be redeclared");
    }

    @Test
    void testAddInstanceThrowsWhenNullParameters() {
        assertParameterNotNull(() -> customContainer.addInstance(null, "customName"));
        assertParameterNotNull(() -> customContainer.addInstance(new Object(), null));
        assertParameterNotNull(() -> customContainer.addInstance(null));
    }

    static Stream<Arguments> testAddInstanceReturnsTrueData() {
        return Stream.of(
                Arguments.of(List.of(new Object())),
                Arguments.of(List.of(new Object(), 5, true, "5", new byte[0], new StringTokenizer("")))
        );
    }
}