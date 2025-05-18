package src.main.java.processSale.model;

import java.math.BigDecimal;

/**
 * Represents the cash compartment of the register.
 * Keeps track of the total cash in the register and notifies observers about
 * revenue updates.
 */
public class RegisterCashCompartment {
    private BigDecimal totalCashInRegister; // Total cash in the register
    private RevenueObserver observer;       // Handles logging total cash to user and file

    /**
     * Constructs a new RegisterCashCompartment with zero initial cash.
     */
    public RegisterCashCompartment() {
        totalCashInRegister = BigDecimal.ZERO;
    }

    /**
     * Sets the observer that will be notified when the total cash changes.
     *
     * @param observer The RevenueObserver to notify.
     */
    public void setObserver(RevenueObserver observer) {
        this.observer = observer;
    }

    /**
     * Adds cash to the register and notifies the current observer.
     *
     * @param addedCash The amount of cash to add.
     * @return The updated total cash in the register.
     * @throws NullPointerException if no observer has been set before calling this
     *                              method.
     */
    public BigDecimal addToCashCompartment(BigDecimal addedCash) {
        totalCashInRegister = totalCashInRegister.add(addedCash);
        notifyObserver();
        return totalCashInRegister;
    }

    /**
     * Notifies the current observer, then sets a file output observer and notifies
     * it as well.
     *
     * @throws NullPointerException if observer is null.
     */
    private void notifyObserver() {
        if (observer == null) {
            throw new NullPointerException("RevenueObserver is not set.");
        }
        observer.logSumOfPayments(totalCashInRegister);
        setObserver(new TotalRevenueFileOutput());
        observer.logSumOfPayments(totalCashInRegister);
    }
}
