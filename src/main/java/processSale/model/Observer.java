package src.main.java.processSale.model;

import java.math.BigDecimal;

public interface Observer {

    void logSumOfPayments(BigDecimal sumOfPayments);


}