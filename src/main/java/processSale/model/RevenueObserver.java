package src.main.java.processSale.model;

import java.math.BigDecimal;

/**
 * Observer interface for receiving updates about the total revenue.
 * Implementing classes are notified whenever the sum of payments is updated.
 */
public interface RevenueObserver {
    /**
     * Called when the total sum of payments is updated.
     *
     * @param sumOfPayments The current total revenue since the program started.
     */
    void logSumOfPayments(BigDecimal sumOfPayments);
}