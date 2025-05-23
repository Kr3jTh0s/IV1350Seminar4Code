package src.main.java.processSale.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.processSale.integration.ConnectionEstablishmentException;

/**
 * Logs error messages and events to a file. Implements the Logger interface to
 * provide persistent error logging.
 */
public class FileLogger implements Logger {
    private PrintWriter logStream;

    /**
     * Creates a new FileLogger and opens the log file for appending.
     * If the file cannot be opened, prints an error message and stack trace.
     */
    public FileLogger() {
        try {
            logStream = new PrintWriter(new FileWriter("out\\ErrorLog.txt", true), true);
        } catch (IOException e) {
            System.out.println("PRINT ERROR!");
            e.printStackTrace();
        }
    }

    /**
     * Logs a message when an illegal argument is encountered.
     *
     * @param exception The {@link IllegalArgumentException} thrown due to an illegal argument.
     */
    @Override
    public void logIllegalArgumentError(IllegalArgumentException exception) {
        logStream.println("IllegalArgumentException: " + exception.getMessage());
    }

    /**
     * Logs a message when an item is not found in the inventory.
     *
     * @param exception The {@link ItemNotFoundException} thrown when the item is not found.
     */
    @Override
    public void logItemNotFound(ItemNotFoundException exception) {
        logStream.println("ItemNotFoundException: " + exception.getMessage());
    }

    /**
     * Logs a message when the payment is insufficient.
     *
     * @param exception The {@link InsufficientPaymentException} thrown when the payment is below the total price.
     */
    @Override
    public void logInsufficientPayment(InsufficientPaymentException exception) {
        logStream.println("InsufficientPaymentException: " + exception.getMessage());
    }

    /**
     * Logs a message when a connection error occurs.
     *
     * @param exception The {@link ConnectionEstablishmentException} thrown when a connection could not be established.
     */
    @Override
    public void logConnectionError(ConnectionEstablishmentException exception) {
        logStream.println("ConnectionEstablishmentException: " + exception.getMessage());
    }

    /**
     * Logs a message when a null pointer is encountered.
     *
     * @param exception The {@link NullPointerException} thrown due to a null reference.
     */
    @Override
    public void logNullPointerError(NullPointerException exception) {
        logStream.println("NullPointerException: " + exception.getMessage());
    }
}
