package src.test.java.model;

import org.junit.jupiter.api.*;

import src.main.java.processSale.integration.ConnectionEstablishmentException;
import src.main.java.processSale.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link FileLogger} class.
 */
class FileLoggerTest {
    private static final String LOG_FILE_PATH = "out\\ErrorLog.txt";
    private FileLogger fileLogger;

    /**
     * Deletes the log file before each test to ensure a clean state.
     */
    @BeforeEach
    void setUp() throws IOException {
        fileLogger = new FileLogger();
    }

    /**
     * Reads the last line from the log file.
     */
    private String getLastLogLine() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line, last = null;
            while ((line = reader.readLine()) != null) {
                last = line;
            }
            return last;
        }
    }

    /**
     * Tests logging an IllegalArgumentException.
     */
    @Test
    void testLogIllegalArgumentError() throws IOException {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        fileLogger.logIllegalArgumentError(ex);
        assertTrue(getLastLogLine().contains("IllegalArgumentException: Invalid argument"),
                "Log should contain the IllegalArgumentException message.");
    }

    /**
     * Tests logging an ItemNotFoundException.
     */
    @Test
    void testLogItemNotFound() throws IOException {
        ItemNotFoundException ex = new ItemNotFoundException("123", "001");
        fileLogger.logItemNotFound(ex);
        assertTrue(getLastLogLine().contains("ItemNotFoundException:"),
                "Log should contain the ItemNotFoundException message.");
    }

    /**
     * Tests logging an InsufficientPaymentException.
     */
    @Test
    void testLogInsufficientPayment() throws IOException {
        InsufficientPaymentException ex = new InsufficientPaymentException("Too low", null);
        fileLogger.logInsufficientPayment(ex);
        assertTrue(getLastLogLine().contains("InsufficientPaymentException: Too low"),
                "Log should contain the InsufficientPaymentException message.");
    }

    /**
     * Tests logging a ConnectionEstablishmentException.
     */
    @Test
    void testLogConnectionError() throws IOException {
        ConnectionEstablishmentException ex = new ConnectionEstablishmentException("Connection failed", "Inventory");
        fileLogger.logConnectionError(ex);
        assertTrue(getLastLogLine().contains("ConnectionEstablishmentException: Connection failed"),
                "Log should contain the ConnectionEstablishmentException message.");
    }

    /**
     * Tests logging a NullPointerException.
     */
    @Test
    void testLogNullPointerError() throws IOException {
        NullPointerException ex = new NullPointerException("Null pointer");
        fileLogger.logNullPointerError(ex);
        assertTrue(getLastLogLine().contains("NullPointerException: Null pointer"),
                "Log should contain the NullPointerException message.");
    }
}
