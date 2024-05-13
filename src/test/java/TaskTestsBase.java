import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class TaskTestsBase {
    protected CustomContainer customContainer;

    @BeforeEach
    void setup() {
        customContainer = new CustomContainerImpl();
    }

    protected void assertParameterNotNull(Executable executable) {
        assertExceptionMessage(executable, "Null is not allowed as a parameter");
    }

    protected void assertExceptionMessage(Executable executable, String exceptionMessage) {
        {
            RuntimeException e = assertThrows(RuntimeException.class, executable, "Exception should be thrown");
            assertEquals(exceptionMessage, e.getMessage(), "Exception message should match");
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @Getter
    protected static class SimplePOJO {
        private final int number;
        private final String string;
    }
}