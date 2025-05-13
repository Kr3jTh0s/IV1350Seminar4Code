package src.main.java.processSale.model;

import java.math.BigDecimal;

public class RegisterCashCompartment {
    private BigDecimal totalCashInRegister;
    private RevenueObserver observer;

    public RegisterCashCompartment(){
        totalCashInRegister = BigDecimal.ZERO;
    }
    
    public void setObserver(RevenueObserver observer) {
        this.observer = observer;
    }

    public BigDecimal addToCashCompartment(BigDecimal addedCash) {
        totalCashInRegister = totalCashInRegister.add(addedCash);
        notifyObserver();
        setObserver(new TotalRevenueFileOutput());
        notifyObserver();
        return totalCashInRegister;
    }

    private void notifyObserver(){
        observer.logSumOfPayments(totalCashInRegister);
    }
}
