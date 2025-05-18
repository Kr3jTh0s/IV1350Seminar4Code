package src.main.java.processSale.view;

import java.math.BigDecimal;

import src.main.java.processSale.model.RevenueObserver;

/**
 * Displays the total revenue collected since the program started.
 * Implements the RevenueObserver interface to receive updates about revenue
 * changes.
 */
public class TotalRevenueView implements RevenueObserver {

    /**
     * Creates a new instance of TotalRevenueView.
     */
    public TotalRevenueView() {
    }

    /**
     * Called when the total sum of payments is updated.
     * Displays the updated total revenue to the user.
     *
     * @param sumOfPayments The current total revenue since program start.
     */
    @Override
    public void logSumOfPayments(BigDecimal sumOfPayments) {
        System.out.printf("Total revenue since program start: %.2f SEK%n", sumOfPayments);
    }
}