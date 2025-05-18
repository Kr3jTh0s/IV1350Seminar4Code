package src.test.java.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.integration.Discount;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Discount} class.
 */
class DiscountTest {
    private Discount discount;

    /**
     * Sets up a new Discount instance before each test.
     */
    @BeforeEach
    void setUp() {
        discount = new Discount();
    }

    /**
     * Tests retrieving the discount factor for a valid customer ID.
     * Should always return 1.0 (no discount) in the default implementation.
     */
    @Test
    void testGetDiscountForValidCustomerID() {
        assertEquals(1.0, discount.getDiscount("customer123").doubleValue(),
                "Discount factor should be 1.0 for a valid customer ID.");
    }

    /**
     * Tests retrieving the discount factor for an empty customer ID.
     * Should always return 1.0 (no discount).
     */
    @Test
    void testGetDiscountForEmptyCustomerID() {
        assertEquals(1.0, discount.getDiscount("").doubleValue(),
                "Discount factor should be 1.0 for an empty customer ID.");
    }

    /**
     * Tests retrieving the discount factor for a null customer ID.
     * Should always return 1.0 (no discount).
     */
    @Test
    void testGetDiscountForNullCustomerID() {
        assertEquals(1.0, discount.getDiscount(null).doubleValue(),
                "Discount factor should be 1.0 for a null customer ID.");
    }
}