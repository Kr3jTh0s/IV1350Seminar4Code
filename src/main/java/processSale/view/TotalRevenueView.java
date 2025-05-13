package src.main.java.processSale.view;

import java.math.BigDecimal;

import src.main.java.processSale.model.Observer;

public class TotalRevenueView implements Observer {

    public TotalRevenueView() {
    }

    @Override
    public void logSumOfPayments(BigDecimal sumOfPayments) {
        System.out.printf("Total revenue since program start: %.2f SEK%n", sumOfPayments);
    }
}
