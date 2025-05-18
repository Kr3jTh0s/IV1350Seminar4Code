package src.main.java.processSale.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.math.BigDecimal;

import src.main.java.processSale.model.dto.*;

/**
 * Represents a sale transaction, including details such as the time of sale,
 * items purchased, running total, and VAT. Provides methods to manage and
 * retrieve information about the sale.
 */
public class Sale {
    private final TimeOfSaleDTO timeOfSale;             // Time when the sale was initiated
    private final ItemList items;                       // List of items in the sale
    private BigDecimal runningTotal;                    // Total cost of items in the sale
    private BigDecimal totalVAT;                        // Total VAT for the sale
    private final RegisterCashCompartment cashRegister; // Cash register for this sale

    /**
     * Initializes a new sale with the current time and an empty item list.
     *
     * @param cashRegister The cash register compartment associated with this sale.
     */
    public Sale(RegisterCashCompartment cashRegister) {
        this.items = new ItemList();
        this.timeOfSale = new TimeOfSaleDTO(new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(Calendar.getInstance().getTime()));
        this.runningTotal = BigDecimal.ZERO;
        this.totalVAT = BigDecimal.ZERO;
        this.cashRegister = cashRegister;
    }

    /**
     * Retrieves the time of the sale.
     *
     * @return A {@link TimeOfSaleDTO} object representing the date and time
     *         when the sale was initiated.
     */
    public TimeOfSaleDTO getTimeOfSale() {
        return timeOfSale;
    }

    /**
     * Checks if an item exists in the current sale based on its item ID.
     *
     * @param itemID The unique identifier of the item to check.
     * @return {@code true} if the item exists in the sale, otherwise {@code false}.
     */
    public boolean itemExists(String itemID) {
        return items.checkItem(itemID);
    }

    /**
     * Increases the quantity of an item in the current sale based on its item ID.
     * If the item does not exist in the sale, no action is taken.
     *
     * @param itemID The unique identifier of the item whose quantity is to be
     *               increased.
     * @return A string describing the updated item and the current totals.
     */
    public String increaseItemQuantity(String itemID) {
        String addedItem = items.increaseQuantity(itemID);
        updateSale(items.getItem(itemID));
        return addedItem + printTotals();
    }

    /**
     * Adds a new item to the current sale and updates the sale totals.
     *
     * @param item The {@link ItemDTO} object representing the item to be added.
     * @return A string describing the added item and the updated sale totals.
     */
    public String addItem(ItemDTO item) {
        String addedItem = items.addNewItem(item);
        updateSale(item);
        return addedItem + printTotals();
    }

    /**
     * Updates the running total and VAT for the sale based on the given item.
     *
     * @param item The {@link ItemDTO} object used to update the totals.
     */
    private void updateSale(ItemDTO item) {
        runningTotal = runningTotal.add(item.getPrice());
        totalVAT = totalVAT.add(item.getPrice().multiply(item.getVATRate()));
    }

    /**
     * Creates a printout of the current sale's total cost and VAT.
     *
     * @return The current sale's total cost and VAT as a string.
     */
    private String printTotals() {
        return String.format("Total cost (incl. VAT): %.2f SEK%nTotal VAT: %.2f SEK%n%n",
                runningTotal, totalVAT);
    }

    /**
     * Retrieves the current running total for the sale.
     *
     * @return The running total as a {@link BigDecimal}.
     */
    public BigDecimal getRunningTotal() {
        return runningTotal;
    }

    /**
     * Processes the sale by finalizing payment and generating a summary.
     * Throws an exception if the payment is insufficient.
     *
     * @param amountPaid The amount paid by the customer.
     * @return A {@link SaleSummaryDTO} containing the sale details, payment info,
     *         and purchased items.
     * @throws InsufficientPaymentException if the payment is less than the total
     *                                      price.
     */
    public SaleSummaryDTO processSale(BigDecimal amountPaid) throws InsufficientPaymentException {
        ProcessPayment processedPayment = new ProcessPayment(amountPaid, runningTotal, cashRegister);
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO(
                amountPaid,
                processedPayment.getChange(),
                runningTotal,
                totalVAT);
        return new SaleSummaryDTO(timeOfSale, items.getBoughtItemsDTO(), paymentInfo);
    }
}