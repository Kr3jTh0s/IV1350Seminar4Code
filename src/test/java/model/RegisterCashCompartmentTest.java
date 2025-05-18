package src.test.java.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.model.RegisterCashCompartment;
import src.main.java.processSale.model.RevenueObserver;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RegisterCashCompartment} class.
 */
class RegisterCashCompartmentTest {
    private RegisterCashCompartment register;
    private TestObserver observer;

    /**
     * Simple test observer that records the last value it was notified with.
     */
    static class TestObserver implements RevenueObserver {
        BigDecimal lastNotifiedValue = null;

        @Override
        public void logSumOfPayments(BigDecimal totalRevenue) {
            lastNotifiedValue = totalRevenue;
        }
    }

    /**
     * Sets up a new RegisterCashCompartment and observer before each test.
     */
    @BeforeEach
    void setUp() {
        register = new RegisterCashCompartment();
        observer = new TestObserver();
    }

    /**
     * Tests that adding cash updates the total and notifies the observer.
     */
    @Test
    void testAddToCashCompartmentNotifiesObserver() {
        register.setObserver(observer);
        BigDecimal added = new BigDecimal("100.00");
        BigDecimal result = register.addToCashCompartment(added);

        assertEquals(added, result, "Total cash should match the added amount.");
        assertEquals(added, observer.lastNotifiedValue, "Observer should be notified with the correct value.");
    }

    /**
     * Tests that adding cash twice accumulates the total and notifies the observer.
     */
    @Test
    void testAddToCashCompartmentMultipleTimes() {
        register.setObserver(observer);
        register.addToCashCompartment(new BigDecimal("50.00"));
        register.setObserver(observer);
        BigDecimal result = register.addToCashCompartment(new BigDecimal("25.00"));

        assertEquals(new BigDecimal("75.00"), result, "Total cash should be the sum of all added amounts.");
        assertEquals(new BigDecimal("75.00"), observer.lastNotifiedValue, "Observer should be notified with the updated value.");
    }

    /**
     * Tests that adding cash without setting an observer throws NullPointerException.
     */
    @Test
    void testAddToCashCompartmentWithoutObserverThrowsException() {
        assertThrows(NullPointerException.class, () -> register.addToCashCompartment(new BigDecimal("10.00")),
                "Adding cash without an observer should throw NullPointerException.");
    }

    /**
     * Tests that setting a null observer and then adding cash throws NullPointerException.
     */
    @Test
    void testSetNullObserverAndAddThrowsException() {
        register.setObserver(null);
        assertThrows(NullPointerException.class, () -> register.addToCashCompartment(new BigDecimal("5.00")),
                "Adding cash with a null observer should throw NullPointerException.");
    }
}
