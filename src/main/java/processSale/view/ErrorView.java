package src.main.java.processSale.view;

import java.math.BigDecimal;

import src.main.java.processSale.integration.ConnectionEstablishmentException;
import src.main.java.processSale.model.InsufficientPaymentException;
import src.main.java.processSale.model.ItemNotFoundException;
import src.main.java.processSale.model.Logger;

/**
 * Handles and displays error messages to the user. Implements the Logger
 * interface to provide feedback for various error scenarios.
 */
public class ErrorView implements Logger {

    /**
     * Creates a new instance of ErrorView.
     */
    public ErrorView() {
    }

    /**
     * Displays a message when an illegal argument is encountered.
     *
     * @param exception The {@link IllegalArgumentException} thrown due to an
     *                  illegal argument.
     */
    @Override
    public void logIllegalArgumentError(IllegalArgumentException exception) {
        System.out.println("An error has occurred: " + exception.getMessage());
    }

    /**
     * Displays a message when an item is not found in the inventory.
     *
     * @param exception The {@link ItemNotFoundException} thrown when the item is
     *                  not found.
     */
    @Override
    public void logItemNotFound(ItemNotFoundException exception) {
        System.out.println("Item not found in inventory: " + exception.getItemNotFoundID());
    }

    /**
     * Displays a message when the payment is insufficient.
     *
     * @param exception The {@link InsufficientPaymentException} thrown when the
     *                  payment is below the total price.
     */
    @Override
    public void logInsufficientPayment(InsufficientPaymentException exception) {
        System.out.println("Insufficient payment. The paid amount is " + exception.getAmountBelowTotalPrice()
                + " below total price.");
    }

    /**
     * Displays a message when a connection error occurs.
     *
     * @param exception The {@link ConnectionEstablishmentException} thrown when a
     *                  connection could not be established.
     */
    @Override
    public void logConnectionError(ConnectionEstablishmentException exception) {
        System.out.println(
                "Connection could not be established with " + exception.getSource() + ". Please try again later.\n");
    }

    /**
     * Displays a message when a null pointer is encountered.
     *
     * @param exception The {@link NullPointerException} thrown due to a null
     *                  reference.
     */
    @Override
    public void logNullPointerError(NullPointerException exception) {
        System.out.println("An error has occurred: " + exception.getMessage());
    }
}
