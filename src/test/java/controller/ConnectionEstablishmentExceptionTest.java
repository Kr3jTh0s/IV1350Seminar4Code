package src.test.java.controller;

import org.junit.jupiter.api.Test;
import src.main.java.processSale.controller.ConnectionEstablishmentException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ConnectionEstablishmentException} class.
 */
class ConnectionEstablishmentExceptionTest {

    /**
     * Tests that the exception message and source are set correctly.
     */
    @Test
    void testExceptionMessageAndSource() {
        String message = "Connection failed";
        String source = "External Inventory System";
        ConnectionEstablishmentException exception = new ConnectionEstablishmentException(message, source);

        assertEquals(message, exception.getMessage(), "Exception message should match the input message.");
        assertEquals(source, exception.getSource(), "Exception source should match the input source.");
    }

    /**
     * Tests that the exception can be thrown and caught.
     */
    @Test
    void testThrowAndCatchException() {
        assertThrows(ConnectionEstablishmentException.class, () -> {
            throw new ConnectionEstablishmentException("Failed", "TestSystem");
        }, "Should throw ConnectionEstablishmentException.");
    }

    /**
     * Tests that the source can be null and is handled correctly.
     */
    @Test
    void testNullSource() {
        ConnectionEstablishmentException exception = new ConnectionEstablishmentException("Error", null);
        assertNull(exception.getSource(), "Source should be null if passed as null.");
    }
}
