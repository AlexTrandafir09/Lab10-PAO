import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Tests extends TaskTestsBase {
    @Test
    void testClose() {
        DummyCloseable firstCloseable = new DummyCloseable();
        DummyCloseable secondCloseable = new DummyCloseable();

        customContainer.addInstance(firstCloseable);
        customContainer.addInstance(true);
        customContainer.addInstance(secondCloseable, "otherCloseable");
        customContainer.addInstance(new Object());

        assertDoesNotThrow(() -> customContainer.close());

        assertTrue(firstCloseable.isClosed());
        assertTrue(secondCloseable.isClosed());
    }


    @Getter
    private class DummyCloseable implements Closeable {

        boolean closed = false;

        @Override
        public void close() throws IOException {
            closed = true;
        }
    }
}