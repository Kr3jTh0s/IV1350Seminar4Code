package src.main.java.processSale.model;

import java.math.BigDecimal;

public class InsufficientPaymentException extends Exception {

    //Do not need to be caught or declared.
    //Typically represent programming errors (e.g., null pointers, out-of-bounds).
    //Subclasses of RuntimeException.
    //Compiler does not enforce handling.

    //pay how much u want: bajs

    private BigDecimal amountBelowTotalPrice;

    public InsufficientPaymentException(String msg, BigDecimal amountBelowTotalPrice){
        super(msg);
        this.amountBelowTotalPrice = amountBelowTotalPrice;
    }

    public BigDecimal getAmountBelowTotalPrice(){
        return amountBelowTotalPrice;
    }
}
