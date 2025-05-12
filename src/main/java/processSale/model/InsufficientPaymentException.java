package src.main.java.processSale.model;

import java.math.BigDecimal;

public class InsufficientPaymentException extends Exception {
    private BigDecimal amountBelowTotalPrice;

    public InsufficientPaymentException(String msg, BigDecimal amountBelowTotalPrice){
        super(msg);
        this.amountBelowTotalPrice = amountBelowTotalPrice;
    }

    public BigDecimal getAmountBelowTotalPrice(){
        return amountBelowTotalPrice;
    }
}
