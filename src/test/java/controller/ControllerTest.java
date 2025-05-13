package src.test.java.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.controller.Controller;
import src.main.java.processSale.integration.*;
import src.main.java.processSale.model.ItemNotFoundException;
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
     * Tests registering an item in the sale.
     */
    @Test
    void testRegisterItem() {
        controller.startSale();

        assertDoesNotThrow(() -> controller.registerItem("1"), "Registering a valid item should not throw an exception.");
        controller.registerItem("1");
        assertDoesNotThrow(() -> controller.registerItem("1"), "Registering the same item again should not throw an exception.");
        assertDoesNotThrow(() -> controller.registerItem("error"), "Registering an invalid item should not throw an exception.");
        //assertThrows(ItemNotFoundException.class, () -> controller.registerItem("10"), "Registering an non existing item should throw an exception");
    }

    /**
     * Tests ending a sale.
     */
    @Test
    void testEndSale() {
        controller.startSale();
        controller.registerItem("1");
        assertDoesNotThrow(() -> controller.endSale("customer123"), "Ending a sale should not throw an exception.");
    }

    /**
     * Tests processing a sale with valid payment.
     */
    @Test
    void testProcessSaleWithValidPayment() {
        controller.startSale();
        controller.registerItem("1");
        controller.endSale("customer123");

        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(10.0)), "Processing a sale with exact payment should not throw an exception.");
        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(15.0)), "Processing a sale with overpayment should not throw an exception.");
    }

    /**
     * Tests processing a sale with insufficient payment.
     */
    @Test
    void testProcessSaleWithInsufficientPayment() {
        controller.startSale();
        controller.registerItem("1");
        controller.endSale("customer123");

        assertDoesNotThrow(() -> controller.processSale(new BigDecimal(5.0)), "Processing a sale with insufficient payment should not throw an exception.");
    }

    /**
     * Tests registering an item without starting a sale.
     */
    @Test
    void testRegisterItemWithoutStartingSale() {
        assertThrows(NullPointerException.class, () -> controller.registerItem("1"), "Registering an item without starting a sale should throw a NullPointerException.");
    }

    /**
     * Tests ending a sale without starting one.
     */
    @Test
    void testEndSaleWithoutStartingSale() {
        assertThrows(NullPointerException.class, () -> controller.endSale("customer123"), "Ending a sale without starting one should throw a NullPointerException.");
    }

    /**
     * Tests processing a sale without starting one.
     */
    @Test
    void testProcessSaleWithoutStartingSale() {
        assertThrows(NullPointerException.class, () -> controller.processSale(new BigDecimal(10.0)), "Processing a sale without starting one should throw a NullPointerException.");
    }
}