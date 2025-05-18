package src.main.java.processSale.model;

import java.math.BigDecimal;

/**
 * Thrown to indicate that the payment provided by the customer is insufficient
 * to cover the total price of the sale. This is a checked exception (subclass
 * of Exception).
 */
public class InsufficientPaymentException extends Exception {

    private final BigDecimal amountBelowTotalPrice;

    /**
     * Constructs a new InsufficientPaymentException with the specified detail
     * message and the amount by which the payment is below the total price.
     *
     * @param msg                   The detail message explaining the reason for the
     *                              exception.
     * @param amountBelowTotalPrice The amount by which the payment is less than the
     *                              total price.
     */
    public InsufficientPaymentException(String msg, BigDecimal amountBelowTotalPrice) {
        super(msg);
        this.amountBelowTotalPrice = amountBelowTotalPrice;
    }

    /**
     * Returns the amount by which the payment is below the total price.
     *
     * @return The insufficient payment amount.
     */
    public BigDecimal getAmountBelowTotalPrice() {
        return amountBelowTotalPrice;
    }
}
