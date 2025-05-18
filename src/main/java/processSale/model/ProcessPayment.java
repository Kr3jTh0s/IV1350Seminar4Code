package src.main.java.processSale.model;

import java.math.BigDecimal;

/**
 * Handles the processing of a payment, including calculating the change to give
 * back and updating the cash register.
 */
class ProcessPayment {
    private final BigDecimal changeToGiveBack; // The amount of change to return to the customer

    /**
     * Creates a new instance of ProcessPayment, calculates the change to give back,
     * and updates the cash register with the total price.
     *
     * @param amountPaid   The amount paid by the customer.
     * @param totalPrice   The total price of the sale.
     * @param cashRegister The cash register compartment to update.
     * @throws InsufficientPaymentException if the payment is less than the total
     *                                      price.
     */
    public ProcessPayment(BigDecimal amountPaid, BigDecimal totalPrice, RegisterCashCompartment cashRegister)
            throws InsufficientPaymentException {
        this.changeToGiveBack = calculateChange(amountPaid, totalPrice);
        cashRegister.addToCashCompartment(totalPrice);
    }

    /**
     * Calculates the change to give back to the customer.
     *
     * @param amountPaid The amount paid by the customer.
     * @param totalPrice The total price of the sale.
     * @return The calculated change.
     * @throws InsufficientPaymentException if the payment is less than the total
     *                                      price.
     */
    private BigDecimal calculateChange(BigDecimal amountPaid, BigDecimal totalPrice)
            throws InsufficientPaymentException {
        BigDecimal change = amountPaid.subtract(totalPrice);
        if (change.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientPaymentException("The calculated change resulted in a negative value: " +
                    amountPaid + " - " + totalPrice + " = " + change + ".", change);
        }
        return change;
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
