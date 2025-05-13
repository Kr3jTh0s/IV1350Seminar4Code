package src.main.java.processSale.model;

import java.math.BigDecimal;

public interface RevenueObserver {
    void logSumOfPayments(BigDecimal sumOfPayments);
}