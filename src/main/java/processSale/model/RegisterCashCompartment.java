package src.main.java.processSale.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.main.java.processSale.view.TotalRevenueFileOutput;

/**
 * Represents the cash compartment of the register.
 * Keeps track of the total cash in the register and notifies observers about
 * revenue updates.
 */
public class RegisterCashCompartment {
    private BigDecimal totalCashInRegister;     // Total cash in the register
    private List<RevenueObserver> observers;    // Handles logging total cash to user and file

    /**
     * Constructs a new RegisterCashCompartment with zero initial cash.
     */
    public RegisterCashCompartment() {
        totalCashInRegister = BigDecimal.ZERO;
        observers = new ArrayList<>();
    }

    /**
     * Sets the observer that will be notified when the total cash changes.
     *
     * @param observer The RevenueObserver to notify.
     */
    public void addObserver(RevenueObserver newObserver) {
        observers.add(newObserver);
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
        for(RevenueObserver observer : observers) {
            if (observer == null) {
                throw new NullPointerException("RevenueObserver is not set.");
            }
            observer.logSumOfPayments(totalCashInRegister);
        }
    }
}
