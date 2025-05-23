package src.test.java.model;

import org.junit.jupiter.api.*;

import src.main.java.processSale.model.FileLogger;
import src.main.java.processSale.view.TotalRevenueFileOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TotalRevenueFileOutput} class.
 * <p>
 * These tests verify that revenue logging to file works as expected,
 * including handling of null values and correct file output.
 * </p>
 */
class TotalRevenueFileOutputTest {
    private static final String LOG_FILE_PATH = "out\\TotalRevenueFileOutput.txt";
    private TotalRevenueFileOutput totalRevenueFileOutput;

    /**
     * Initializes a new {@link TotalRevenueFileOutput} before each test.
     *
     * @throws IOException if the logger cannot be created (should not happen in normal test environments)
     */
    @BeforeEach
    void setUp() throws IOException {
        totalRevenueFileOutput = new TotalRevenueFileOutput();
    }

    /**
     * Reads and returns the last line from the log file.
     *
     * @return The last line in the log file, or {@code null} if the file is empty.
     * @throws IOException if the file cannot be read.
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
     * Tests that logging a payment writes the correct line to the file.
     *
     * @throws IOException if the log file cannot be read.
     */
    @Test
    void testLogSumOfPaymentsWritesToFile() throws IOException {
        BigDecimal total = new BigDecimal("123.45");
        totalRevenueFileOutput.logSumOfPayments(total);
        assertNotNull(getLastLogLine(), "Log file should contain at least one line.");
        assertTrue(getLastLogLine().contains("123,45"), "Log should contain the total revenue value.");
        assertTrue(getLastLogLine().contains("Current cash in register"), "Log should contain the expected message.");
    }

    /**
     * Tests that logging a null value does not throw an exception and writes "null" to the file.
     *
     * @throws IOException if the log file cannot be read.
     */
    @Test
    void testLogSumOfPaymentsWithNull() throws IOException {
        assertDoesNotThrow(() -> totalRevenueFileOutput.logSumOfPayments(null),
                "Logging null should not throw an exception.");
        assertNotNull(getLastLogLine(), "Log file should contain at least one line.");
        assertTrue(getLastLogLine().contains("nu"), "Log should contain 'null' when null is logged.");
    }
}
