package src.main.java.processSale.controller;

import java.math.BigDecimal;
import java.net.ConnectException;

import src.main.java.processSale.integration.*;
import src.main.java.processSale.model.*;
import src.main.java.processSale.model.dto.*;
import src.main.java.processSale.view.*;

/**
 * The Controller class manages the flow of the application. It acts as a
 * mediator between the view, model, and integration layers, handling user 
 * input and coordinating updates across the system.
 */
public class Controller {
    private View view;        // The view layer for user interaction
    private Printer printer;  // Handles receipt printing
    private Inventory externalInventory;    // Manages inventory operations
    private Discount discountDatabase;    // Handles discount operations
    private Account externalAccounting;      // Handles accounting operations
    private Sale currentSale; // Represents the ongoing sale
    private RegisterCashCompartment cashRegister;
    private Logger logger;

    /**
     * Initializes the Controller with the required external system dependencies.
     * 
     * @param printer The printer instance for printing receipts.
     * @param inv     The inventory system for retrieving item information.
     * @param disc    The discount system for applying discounts.
     * @param acc     The accounting system for recording transactions.
     */
    public Controller(Printer printer, Inventory externalInventory, Discount discountDatabase, Account externalAccounting, RegisterCashCompartment cashRegister) {
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

    private void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Starts a new sale by creating a new `Sale` instance and initializing the
     * receipt.
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
     */
    public void registerItem(String itemID) {
        if (currentSale.itemExists(itemID)) {
            view.displayAddedItem(currentSale.increaseItemQuantity(itemID));
        } else {
            try {

                //inventory.getItem("nonexisitingid") throw.
                
                if (itemID.equalsIgnoreCase("error")) {
                    throw new ConnectException();
                }
                ItemDTO searchedItem = externalInventory.getItem(itemID);
                view.displayAddedItem(currentSale.addItem(searchedItem));
            } catch (ItemNotFoundException e) {
                setLogger(new ErrorView());
                logger.logItemNotFound(itemID);
                setLogger(new FileLogger());
                logger.logItemNotFound(itemID);
            } catch (ConnectException e) {
                setLogger(new ErrorView());
                logger.logConnectionError("External Inventory System");
                setLogger(new FileLogger());
                logger.logConnectionError("External Inventory System");
            }
        }
    }

    /**
     * Ends the current sale and calculates the total price. This method can be
     * extended to include additional operations, such as applying discounts or
     * notifying the view.
     * 
     * @param customerID The unique identifier of the customer (currently unused).
     */
    public void endSale(String customerID) {
        BigDecimal totalPrice = currentSale.getRunningTotal();
        view.displayTotalPrice(totalPrice);
        // Future extension: Apply discounts or notify the view
    }

    /**
     * Processes the sale by finalizing payment, printing the receipt, and updating
     * the inventory and accounting systems.
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
            setLogger(new ErrorView());
            logger.logInsufficientPayment(e.getAmountBelowTotalPrice());
            setLogger(new FileLogger());
            logger.logInsufficientPayment(e.getAmountBelowTotalPrice());
        }
    }
}
