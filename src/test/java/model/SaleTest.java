package src.test.java.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.main.java.processSale.model.InsufficientPaymentException;
import src.main.java.processSale.model.RegisterCashCompartment;
import src.main.java.processSale.model.Sale;
import src.main.java.processSale.model.dto.ItemDTO;
import src.main.java.processSale.model.dto.SaleSummaryDTO;
import src.main.java.processSale.view.TotalRevenueView;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Unit tests for the {@link Sale} class.
 */
class SaleTest {
    private Sale sale;
    private RegisterCashCompartment cashRegister;
    private ItemDTO testItem1;
    private ItemDTO testItem2;

    /**
     * Sets up a new Sale instance and test items before each test.
     */
    @BeforeEach
    void setUp() {
        cashRegister = new RegisterCashCompartment();
        sale = new Sale(cashRegister);
        testItem1 = new ItemDTO("Apple", "001", "Fresh red apple", new BigDecimal(10.0), new BigDecimal(0.12));
        testItem2 = new ItemDTO("Banana", "002", "Yellow banana", new BigDecimal(15.0), new BigDecimal(0.06));
    }

    /**
     * Tests the initial state of a new Sale instance.
     */
    @Test
    void testInitialState() {
        assertNotNull(sale.getTimeOfSale(), "Time of sale should not be null.");
        assertEquals(new BigDecimal(0.0), sale.getRunningTotal(), "Running total should be 0.0 for a new sale.");
    }

    /**
     * Tests adding a new item to the sale.
     */
    @Test
    void testAddNewItem() {
        sale.addItem(testItem1);
        assertTrue(sale.itemExists("001"), "Item should exist after being added.");
        assertEquals(new BigDecimal(10.0), sale.getRunningTotal(), "Running total should reflect the item's price.");
    }

    /**
     * Tests increasing the quantity of an existing item in the sale.
     */
    @Test
    void testIncreaseItemQuantity() {
        sale.addItem(testItem1);
        sale.increaseItemQuantity("001");
        assertEquals(new BigDecimal(20.0), sale.getRunningTotal(),
                "Running total should reflect the increased quantity.");
    }

    /**
     * Tests checking if an item exists in the sale.
     */
    @Test
    void testItemExists() {
        assertFalse(sale.itemExists("001"), "Item should not exist before being added.");
        sale.addItem(testItem1);
        assertTrue(sale.itemExists("001"), "Item should exist after being added.");
        assertFalse(sale.itemExists("999"), "Non-existent item should not be found.");
    }

    /**
     * Tests processing a sale and generating a summary.
     *
     * @throws InsufficientPaymentException if the payment is less than the total
     *                                      price.
     */
    @Test
    void testProcessSale() throws InsufficientPaymentException {
        cashRegister.setObserver(new TotalRevenueView());
        sale.addItem(testItem1);
        sale.addItem(testItem2);

        SaleSummaryDTO summary = sale.processSale(new BigDecimal(30.0));

        assertEquals(new BigDecimal(25.0), summary.getTotalPrice(), "Total price should match the sum of item prices.");
        assertEquals(new BigDecimal(30.0), summary.getAmountPaid(), "Amount paid should match the input.");
        assertEquals(new BigDecimal(5.0), summary.getChange(), "Change should be correctly calculated.");
        assertEquals(2, summary.getBoughtItems().size(), "Bought items should include all added items.");
    }

    /**
     * Tests the running total with multiple items added to the sale.
     */
    @Test
    void testRunningTotalWithMultipleItems() {
        sale.addItem(testItem1);
        sale.addItem(testItem2);
        assertEquals(new BigDecimal(25.0), sale.getRunningTotal(),
                "Running total should reflect the sum of item prices.");
    }

    /**
     * Tests adding the same item multiple times instead of increasing quantity.
     */
    @Test
    void testAddSameItemMultipleTimes() {
        sale.addItem(testItem1);
        assertThrows(IllegalArgumentException.class, () -> sale.addItem(testItem1),
                "Exception should be thrown if an item is added twice instead of increased.");
    }

    /**
     * Tests processing a sale with insufficient payment.
     * Expects an InsufficientPaymentException to be thrown.
     */
    @Test
    void testProcessSaleWithInsufficientPayment() {
        sale.addItem(testItem1);
        sale.addItem(testItem2);

        assertThrows(InsufficientPaymentException.class, () -> sale.processSale(new BigDecimal(20.0)),
                "Should throw InsufficientPaymentException for insufficient payment.");
    }

    /**
     * Tests adding an item with a high VAT rate and verifying the total VAT.
     *
     * @throws InsufficientPaymentException if the payment is less than the total
     *                                      price.
     */
    @Test
    void testAddItemWithHighVAT() throws InsufficientPaymentException {
        cashRegister.setObserver(new TotalRevenueView());
        ItemDTO expensiveItem = new ItemDTO("Luxury Watch", "003", "High-end watch", new BigDecimal(1000.0),
                new BigDecimal(0.25));
        sale.addItem(expensiveItem);

        assertEquals(new BigDecimal(1000.0), sale.getRunningTotal(), "Running total should reflect the item's price.");
        assertEquals(0, sale.processSale(new BigDecimal(1000.0)).getTotalVAT().compareTo(new BigDecimal(250.0)),
                "Total VAT should be correctly calculated.");
    }
}