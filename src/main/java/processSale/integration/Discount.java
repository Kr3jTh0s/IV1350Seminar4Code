package src.main.java.processSale.integration;

import java.math.BigDecimal;

/**
 * Represents the discount system that calculates discounts for customers.
 * This class can be extended to implement more complex discount logic.
 */
public class Discount {

    /**
     * Creates a new instance of the Discount system. This constructor can 
     * be extended to initialize discount-specific settings.
     */
    public Discount() {
        System.out.println("Discount system initialized.");
    }

    /**
     * Retrieves the discount factor for a given customer.
     * 
     * @param customerID The unique identifier of the customer.
     * @return A discount factor as a double (e.g., 1.0 for no discount, 0.9 for a
     *         10% discount).
     */
    public BigDecimal getDiscount(String customerID) {
        // Placeholder logic: Always return no discount (factor of 1.0)
        return new BigDecimal(1.0);
    }
}
