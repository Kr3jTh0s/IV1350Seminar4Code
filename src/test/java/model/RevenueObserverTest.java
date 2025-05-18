package src.test.java.model;

import org.junit.jupiter.api.Test;
import src.main.java.processSale.model.RevenueObserver;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RevenueObserver} interface.
 * Uses a simple implementation to verify the observer receives correct updates.
 */
class RevenueObserverTest {

    /**
     * Simple implementation of RevenueObserver for testing.
     */
    static class TestRevenueObserver implements RevenueObserver {
        BigDecimal lastSum = null;

        @Override
        public void logSumOfPayments(BigDecimal sumOfPayments) {
            this.lastSum = sumOfPayments;
        }
    }

    /**
     * Tests that the observer receives the correct sum of payments.
     */
    @Test
    void testLogSumOfPayments() {
        TestRevenueObserver observer = new TestRevenueObserver();
        BigDecimal sum = new BigDecimal("123.45");
        observer.logSumOfPayments(sum);
        assertEquals(sum, observer.lastSum, "Observer should receive the correct sum of payments.");
    }

    /**
     * Tests that the observer can handle a null sum of payments.
     */
    @Test
    void testLogSumOfPaymentsWithNull() {
        TestRevenueObserver observer = new TestRevenueObserver();
        observer.logSumOfPayments(null);
        assertNull(observer.lastSum, "Observer should handle null sum of payments.");
    }
}
