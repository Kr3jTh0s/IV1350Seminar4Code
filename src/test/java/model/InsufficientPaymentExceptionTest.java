package src.test.java.model;

import org.junit.jupiter.api.Test;
import src.main.java.processSale.model.InsufficientPaymentException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link InsufficientPaymentException} class.
 */
class InsufficientPaymentExceptionTest {

    /**
     * Tests that the exception message and amount below total price are set correctly.
     */
    @Test
    void testExceptionMessageAndAmount() {
        String message = "Payment is too low";
        BigDecimal amount = new BigDecimal("5.50");
        InsufficientPaymentException exception = new InsufficientPaymentException(message, amount);

        assertEquals(message, exception.getMessage(), "Exception message should match the input message.");
        assertEquals(amount, exception.getAmountBelowTotalPrice(), "Amount below total price should match the input amount.");
    }

    /**
     * Tests that the exception can be thrown and caught.
     */
    @Test
    void testThrowAndCatchException() {
        assertThrows(InsufficientPaymentException.class, () -> {
            throw new InsufficientPaymentException("Insufficient funds", new BigDecimal("10.00"));
        }, "Should throw InsufficientPaymentException.");
    }

    /**
     * Tests that the amount below total price can be null and is handled correctly.
     */
    @Test
    void testNullAmountBelowTotalPrice() {
        InsufficientPaymentException exception = new InsufficientPaymentException("No amount", null);
        assertNull(exception.getAmountBelowTotalPrice(), "Amount below total price should be null if passed as null.");
    }
}
