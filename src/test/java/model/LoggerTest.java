package src.test.java.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.main.java.processSale.integration.ConnectionEstablishmentException;
import src.main.java.processSale.model.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Logger} interface using a simple mock implementation.
 */
class LoggerTest {
    private Logger logger;
    private StringBuilder logOutput;

    /**
     * A simple mock implementation of Logger that appends messages to a StringBuilder.
     */
    static class MockLogger implements Logger {
        private final StringBuilder output;

        MockLogger(StringBuilder output) {
            this.output = output;
        }

        @Override
        public void logItemNotFound(ItemNotFoundException exception) {
            output.append("ItemNotFound: ").append(exception.getMessage()).append("\n");
        }

        @Override
        public void logInsufficientPayment(InsufficientPaymentException exception) {
            output.append("InsufficientPayment: ").append(exception.getMessage()).append("\n");
        }

        @Override
        public void logConnectionError(ConnectionEstablishmentException exception) {
            output.append("ConnectionError: ").append(exception.getMessage()).append("\n");
        }

        @Override
        public void logIllegalArgumentError(IllegalArgumentException exception) {
            output.append("IllegalArgument: ").append(exception.getMessage()).append("\n");
        }

        @Override
        public void logNullPointerError(NullPointerException exception) {
            output.append("NullPointer: ").append(exception.getMessage()).append("\n");
        }
    }

    /**
     * Sets up a new mock logger and output buffer before each test.
     */
    @BeforeEach
    void setUp() {
        logOutput = new StringBuilder();
        logger = new MockLogger(logOutput);
    }

    /**
     * Tests logging an ItemNotFoundException.
     */
    @Test
    void testLogItemNotFound() {
        ItemNotFoundException ex = new ItemNotFoundException("Item missing", "123");
        logger.logItemNotFound(ex);
        assertTrue(logOutput.toString().contains("ItemNotFound: Item missing"), "Should log ItemNotFoundException message.");
    }

    /**
     * Tests logging an InsufficientPaymentException.
     */
    @Test
    void testLogInsufficientPayment() {
        InsufficientPaymentException ex = new InsufficientPaymentException("Too little", null);
        logger.logInsufficientPayment(ex);
        assertTrue(logOutput.toString().contains("InsufficientPayment: Too little"), "Should log InsufficientPaymentException message.");
    }

    /**
     * Tests logging a ConnectionEstablishmentException.
     */
    @Test
    void testLogConnectionError() {
        ConnectionEstablishmentException ex = new ConnectionEstablishmentException("Connection failed", "Inventory");
        logger.logConnectionError(ex);
        assertTrue(logOutput.toString().contains("ConnectionError: Connection failed"), "Should log ConnectionEstablishmentException message.");
    }

    /**
     * Tests logging an IllegalArgumentException.
     */
    @Test
    void testLogIllegalArgumentError() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad argument");
        logger.logIllegalArgumentError(ex);
        assertTrue(logOutput.toString().contains("IllegalArgument: Bad argument"), "Should log IllegalArgumentException message.");
    }

    /**
     * Tests logging a NullPointerException.
     */
    @Test
    void testLogNullPointerError() {
        NullPointerException ex = new NullPointerException("Null ref");
        logger.logNullPointerError(ex);
        assertTrue(logOutput.toString().contains("NullPointer: Null ref"), "Should log NullPointerException message.");
    }
}
