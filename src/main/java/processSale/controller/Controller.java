package src.main.java.processSale.controller;

import java.math.BigDecimal;

import src.main.java.processSale.integration.*;
import src.main.java.processSale.model.*;
import src.main.java.processSale.model.dto.*;
import src.main.java.processSale.view.*;

/**
 * The Controller class manages the flow of the application.
 * It acts as a mediator between the view, model, and integration layers,
 * handling user input and coordinating updates across the system.
 */
public class Controller {
    private final Printer printer;                      // Handles receipt printing
    private final Inventory externalInventory;          // Manages inventory operations
    private final Discount discountDatabase;            // Handles discount operations
    private final Account externalAccounting;           // Handles accounting operations
    private final RegisterCashCompartment cashRegister; // Manages cash in register
    private View view;                                  // The view layer for user interaction
    private Sale currentSale;                           // Represents the ongoing sale
    private Logger logger;                              // Handles logging of errors and events

    /**
     * Initializes the Controller with the required external system dependencies.
     *
     * @param printer            The printer instance for printing receipts.
     * @param externalInventory  The inventory system for retrieving item
     *                           information.
     * @param discountDatabase   The discount system for applying discounts.
     * @param externalAccounting The accounting system for recording transactions.
     * @param cashRegister       The cash register compartment.
     */
    public Controller(Printer printer, Inventory externalInventory, Discount discountDatabase,
            Account externalAccounting, RegisterCashCompartment cashRegister) {
        this.printer = printer;
        this.externalInventory = externalInventory;
        this.discountDatabase = discountDatabase;
        this.externalAccounting = externalAccounting;
        this.cashRegister = cashRegister;
    }

    /**
     * Sets the view instance for the controller, enabling communication with the
     * view layer.
     *
     * @param view The view instance to be set.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Sets the logger instance for error and event logging.
     *
     * @param logger The logger to use.
     */
    private void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Starts a new sale by creating a new Sale instance and initializing the
     * receipt. Also sets up the revenue observer for the cash register.
     */
    public void startSale() {
        cashRegister.setObserver(new TotalRevenueView());
        currentSale = new Sale(cashRegister);
        printer.createReceipt(currentSale.getTimeOfSale());
    }

    /**
     * Registers an item in the current sale. If the item already exists in the
     * sale, its quantity is increased. Otherwise, the item is retrieved from the
     * inventory system and added to the sale.
     *
     * @param itemID The unique identifier of the item to be registered.
     * @throws ConnectionEstablishmentException if connection could not be
     *                                          established with the external
     *                                          inventory system
     */
    public void registerItem(String itemID) {
        if (currentSale.itemExists(itemID)) {
            view.displayAddedItem(currentSale.increaseItemQuantity(itemID));
            return;
        }
        try {
            // Simulate a connection error for testing
            if (itemID.equalsIgnoreCase("error")) {
                throw new ConnectionEstablishmentException(
                        "External Inventory System socket could not be reached.",
                        "External Inventory System");
            }
            ItemDTO searchedItem = externalInventory.getItem(itemID);
            view.displayAddedItem(currentSale.addItem(searchedItem));
        } catch (IllegalArgumentException e) {
            logIllegalArgumentError(e);
        } catch (ConnectionEstablishmentException e) {
            logConnectionError(e);
        } catch (ItemNotFoundException e) {
            logItemNotFound(e);
        }
    }

    /**
     * Ends the current sale and calculates the total price.
     * This method can be extended to include additional operations,
     * such as applying discounts or notifying the view.
     *
     * @param customerID The unique identifier of the customer (currently unused).
     */
    public void endSale(String customerID) {
        BigDecimal totalPrice = currentSale.getRunningTotal();
        view.displayTotalPrice(totalPrice);
        // Future extension: Apply discounts or notify the view
    }

    /**
     * Processes the sale by finalizing payment, printing the receipt,
     * and updating the inventory and accounting systems.
     *
     * Handles InsufficientPaymentException if the payment is too low,
     * logging the error to both the user and a file.
     *
     * @param amountPaid The amount paid by the customer.
     */
    public void processSale(BigDecimal amountPaid) {
        try {
            SaleSummaryDTO saleSummary = currentSale.processSale(amountPaid);
            printer.printReceipt(saleSummary);
            externalInventory.updateInventory(saleSummary);
            externalAccounting.accountSale(saleSummary);
        } catch (InsufficientPaymentException e) {
            logInsufficientPayment(e);
        } catch (NullPointerException e) {
            logNullPointerError(e);
        }
    }

    /**
     * Logs an illegal argument error to both the user and a file.
     *
     * @param exception The IllegalArgumentException to log.
     */
    private void logIllegalArgumentError(IllegalArgumentException exception) {
        setLogger(new ErrorView());
        logger.logIllegalArgumentError(exception);
        setLogger(new FileLogger());
        logger.logIllegalArgumentError(exception);
    }

    /**
     * Logs a connection error to both the user and a file.
     *
     * @param exception The ConnectionEstablishmentException to log.
     */
    private void logConnectionError(ConnectionEstablishmentException exception) {
        setLogger(new ErrorView());
        logger.logConnectionError(exception);
        setLogger(new FileLogger());
        logger.logConnectionError(exception);
    }

    /**
     * Logs an item-not-found error to both the user and a file.
     *
     * @param exception The ItemNotFoundException to log.
     */
    private void logItemNotFound(ItemNotFoundException exception) {
        setLogger(new ErrorView());
        logger.logItemNotFound(exception);
        setLogger(new FileLogger());
        logger.logItemNotFound(exception);
    }

    /**
     * Logs an insufficient payment error to both the user and a file.
     *
     * @param exception The InsufficientPaymentException to log.
     */
    private void logInsufficientPayment(InsufficientPaymentException exception) {
        setLogger(new ErrorView());
        logger.logInsufficientPayment(exception);
        setLogger(new FileLogger());
        logger.logInsufficientPayment(exception);
    }

    /**
     * Logs a null pointer error to both the user and a file.
     *
     * @param exception The NullPointerException to log.
     */
    private void logNullPointerError(NullPointerException exception) {
        setLogger(new ErrorView());
        logger.logNullPointerError(exception);
        setLogger(new FileLogger());
        logger.logNullPointerError(exception);
    }
}
