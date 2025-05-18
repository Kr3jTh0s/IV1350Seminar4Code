package src.main.java.processSale.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Logs the total revenue to a file each time a payment is recorded.
 * Implements the RevenueObserver interface to receive updates about revenue
 * changes.
 */
public class TotalRevenueFileOutput implements RevenueObserver {
    private PrintWriter logStream;

    /**
     * Creates a new TotalRevenueFileOutput and opens the log file for appending.
     * If the file cannot be opened, prints an error message and stack trace.
     */
    public TotalRevenueFileOutput() {
        try {
            logStream = new PrintWriter(new FileWriter("out\\TotalRevenueFileOutput.txt", true), true);
        } catch (IOException e) {
            System.out.println("PRINT ERROR!");
            e.printStackTrace();
        }
    }

    /**
     * Logs the current total revenue to the file when a new payment is recorded.
     * Closes the log file after writing.
     *
     * @param totalPrice The current total revenue to log.
     */
    @Override
    public void logSumOfPayments(BigDecimal totalPrice) {
        logStream.printf("New payment recorded. Current cash in register: %.2f SEK%n", totalPrice);
        logStream.close();
    }
}