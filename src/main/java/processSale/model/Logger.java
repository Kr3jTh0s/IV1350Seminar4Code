package src.main.java.processSale.model;

import java.math.BigDecimal;

import src.main.java.processSale.controller.ConnectionEstablishmentException;

/**
 * Logger interface for handling and displaying error messages or events.
 * Implementing classes provide feedback for various error scenarios such as
 * item not found, insufficient payment, connection errors, and other
 * exceptions.
 */
public interface Logger {

    /**
     * Logs or displays a message when an item is not found in the inventory.
     *
     * @param exception The {@link ItemNotFoundException} thrown when the item is
     *                  not found.
     */
    void logItemNotFound(ItemNotFoundException exception);

    /**
     * Logs or displays a message when the payment is insufficient.
     *
     * @param exception The {@link InsufficientPaymentException} thrown when the
     *                  payment is below the total price.
     */
    void logInsufficientPayment(InsufficientPaymentException exception);

    /**
     * Logs or displays a message when a connection error occurs.
     *
     * @param exception The {@link ConnectionEstablishmentException} thrown when a
     *                  connection could not be established.
     */
    void logConnectionError(ConnectionEstablishmentException exception);

    /**
     * Logs or displays a message when an illegal argument is encountered.
     *
     * @param exception The {@link IllegalArgumentException} thrown due to an
     *                  illegal argument.
     */
    void logIllegalArgumentError(IllegalArgumentException exception);

    /**
     * Logs or displays a message when a null pointer is encountered.
     *
     * @param exception The {@link NullPointerException} thrown due to a null
     *                  reference.
     */
    void logNullPointerError(NullPointerException exception);

}
