package src.test.java.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.controller.Controller;
import src.main.java.processSale.integration.*;
import src.main.java.processSale.model.RegisterCashCompartment;
import src.main.java.processSale.view.View;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Unit tests for the {@link Controller} class.
 */
class ControllerTest {
    private Controller controller;
    private Printer printer;
    private Inventory inventory;
    private Discount discount;
    private Account account;
    private RegisterCashCompartment register;
    private View view;

    /**
     * Sets up a new Controller instance and its dependencies before each test.
     */
    @BeforeEach
    void setUp() {
        printer = new Printer();
        inventory = new Inventory();
        discount = new Discount();
        account = new Account();
        register = new RegisterCashCompartment();
        controller = new Controller(printer, inventory, discount, account, register);
        view = new View(controller);
    }

    /**
     * Tests starting a new sale.
     */
    @Test
    void testStartSale() {
        assertDoesNotThrow(() -> controller.startSale(), "Starting a sale should not throw an exception.");
    }

    /**
     * Tests registering an item in the sale, including valid, duplicate, and error
     * cases.
     */
    @Test
    void testRegisterItem() {
        controller.startSale();
        assertDoesNotThrow(() -> controller.registerItem("1"),
                "Registering a valid item should not throw an exception.");
        assertDoesNotThrow(() -> controller.registerItem("1"),
                "Registering the same item again should not throw an exception.");
        assertDoesNotThrow(() -> controller.registerItem("error"),
                "Registering an item that triggers a connection error should not throw an exception.");
        assertDoesNotThrow(() -> controller.registerItem("999"),
                "Registering a non-existent item should not throw an exception (error is logged).");
    }

    /**
     * Tests ending a sale after registering an item.
     */
    @Test
    void testEndSale() {
        controller.startSale();
        controller.registerItem("1");
        assertDoesNotThrow(() -> controller.endSale("customer123"), "Ending a sale should not throw an exception.");
    }

    /**
     * Tests processing a sale with valid payment (exact and overpayment).
     */
    @Test
    void testProcessSaleWithValidPayment() {
        controller.startSale();
        controller.registerItem("1");
        controller.endSale("customer123");
        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(10.0)),
                "Processing a sale with exact payment should not throw an exception.");
        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(15.0)),
                "Processing a sale with overpayment should not throw an exception.");
    }

    /**
     * Tests processing a sale with insufficient payment.
     * Should not throw, but should log the error.
     */
    @Test
    void testProcessSaleWithInsufficientPayment() {
        controller.startSale();
        controller.registerItem("1");
        controller.endSale("customer123");
        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(5.0)),
                "Processing a sale with insufficient payment should not throw an exception.");
    }

    /**
     * Tests registering an item without starting a sale should throw
     * NullPointerException.
     */
    @Test
    void testRegisterItemWithoutStartingSale() {
        assertThrows(NullPointerException.class, () -> controller.registerItem("1"),
                "Registering an item without starting a sale should throw a NullPointerException.");
    }

    /**
     * Tests ending a sale without starting one should throw NullPointerException.
     */
    @Test
    void testEndSaleWithoutStartingSale() {
        assertThrows(NullPointerException.class, () -> controller.endSale("customer123"),
                "Ending a sale without starting one should throw a NullPointerException.");
    }

    /**
     * Tests processing a sale without starting one should not throw
     * NullPointerException.
     * The implementation logs the error instead.
     */
    @Test
    void testProcessSaleWithoutStartingSale() {
        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(10.0)),
                "Processing a sale without starting one should not throw a NullPointerException.");
    }

    /**
     * Tests that registering a null item ID throws a NullPointerException.
     */
    @Test
    void testRegisterNullItemIDThrowsException() {
        controller.startSale();
        assertThrows(NullPointerException.class, () -> controller.registerItem(null),
                "Registering a null item ID should throw NullPointerException.");
    }

    /**
     * Tests that calling processSale with a null payment does not throw but is
     * handled.
     */
    @Test
    void testProcessSaleWithNullPayment() {
        controller.startSale();
        controller.registerItem("1");
        controller.endSale("customer123");
        assertDoesNotThrow(() -> controller.processSale(null),
                "Processing a sale with null payment should not throw an exception.");
    }
}