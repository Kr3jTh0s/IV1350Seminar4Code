package src.main.java.processSale.model;

import java.math.BigDecimal;

class RegisterCashCompartment {
    private BigDecimal totalCashInRegister;

    public RegisterCashCompartment(){
        totalCashInRegister = BigDecimal.ZERO;
    }

    public BigDecimal addToCashCompartment(BigDecimal addedCash) {
        totalCashInRegister = totalCashInRegister.add(addedCash);
        return totalCashInRegister;
    }
}
