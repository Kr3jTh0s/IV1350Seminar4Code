package src.main.java.processSale.view;

import java.math.BigDecimal;

import src.main.java.processSale.model.Logger;

public class ErrorView implements Logger {

    public ErrorView(){
    }

    @Override
    public void logItemNotFound(String itemID) {
        System.out.println("Item not found in inventory: " + itemID);
    }
    
    public void logInsufficientPayment(BigDecimal insufficientPayment) {
        System.out.println("Insufficient payemnt. The payed amount is " + insufficientPayment + " below total price.");
    }

    public void logConnectionError(String source) {
        System.out.println("Connection could not be established with " + source + ". Please try again later.\n");
    }
}
