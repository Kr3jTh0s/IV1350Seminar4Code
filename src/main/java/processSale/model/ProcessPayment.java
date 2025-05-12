package src.main.java.processSale.model;

import java.math.BigDecimal;

/**
 * Handles the processing of a payment, including calculating the change to give
 * back.
 */
class ProcessPayment {
    private final BigDecimal changeToGiveBack; // The amount of change to return to the customer

    /**
     * Creates a new instance of ProcessPayment and calculates the change to give
     * back.
     * 
     * @param amountPaid The amount paid by the customer.
     * @param totalPrice The total price of the sale.
     */
    public ProcessPayment(BigDecimal amountPaid, BigDecimal totalPrice) {
        this.changeToGiveBack = calculateChange(amountPaid, totalPrice);
    }

    /**
     * Calculates the change to give back to the customer.
     * 
     * @param amountPaid The amount paid by the customer.
     * @param totalPrice The total price of the sale.
     * @return The calculated change.
     */
    private BigDecimal calculateChange(BigDecimal amountPaid, BigDecimal totalPrice) {
        return amountPaid.subtract(totalPrice);
    }

    /**
     * Retrieves the calculated change to give back to the customer.
     * 
     * @return The change amount.
     */
    public BigDecimal getChange() {
        return changeToGiveBack;
    }
}
