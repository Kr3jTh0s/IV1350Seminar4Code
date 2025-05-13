package src.main.java.processSale.model;

import java.math.BigDecimal;

public interface Logger {
    
    public void logItemNotFound(String itemID);
    
    public void logInsufficientPayment(BigDecimal insufficientPayment);

    public void logConnectionError(String source);

}
