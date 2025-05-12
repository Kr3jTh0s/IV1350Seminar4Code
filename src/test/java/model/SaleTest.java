package src.test.java.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.main.java.processSale.model.Sale;
import src.main.java.processSale.model.dto.ItemDTO;
import src.main.java.processSale.model.dto.SaleSummaryDTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Sale} class.
 */
class SaleTest {
    private Sale sale;
    private ItemDTO testItem1;
    private ItemDTO testItem2;

    /**
     * Sets up a new Sale instance and test items before each test.
     */
    @BeforeEach
    void setUp() {
        sale = new Sale();
        testItem1 = new ItemDTO("Apple", "001", "Fresh red apple", 10.0, 0.12);
        testItem2 = new ItemDTO("Banana", "002", "Yellow banana", 15.0, 0.06);
    }

    /**
     * Tests the initial state of a new Sale instance.
     */
    @Test
    void testInitialState() {
        assertNotNull(sale.getTimeOfSale(), "Time of sale should not be null.");
        assertEquals(0.0, sale.getRunningTotal(), "Running total should be 0.0 for a new sale.");
    }

    /**
     * Tests adding a new item to the sale.
     */
    @Test
    void testAddNewItem() {
        sale.addItem(testItem1);
        assertTrue(sale.itemExists("001"), "Item should exist after being added.");
        assertEquals(10.0, sale.getRunningTotal(), "Running total should reflect the item's price.");
    }

    /**
     * Tests increasing the quantity of an existing item in the sale.
     */
    @Test
    void testIncreaseItemQuantity() {
        sale.addItem(testItem1);
        sale.increaseItemQuantity("001");
        assertEquals(20.0, sale.getRunningTotal(), "Running total should reflect the increased quantity.");
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
     */
    @Test
    void testProcessSale() {
        sale.addItem(testItem1);
        sale.addItem(testItem2);

        SaleSummaryDTO summary = sale.processSale(30.0);

        assertEquals(25.0, summary.getTotalPrice(), "Total price should match the sum of item prices.");
        assertEquals(30.0, summary.getAmountPaid(), "Amount paid should match the input.");
        assertEquals(5.0, summary.getChange(), "Change should be correctly calculated.");
        assertEquals(2, summary.getBoughtItems().size(), "Bought items should include all added items.");
    }

    /**
     * Tests the running total with multiple items added to the sale.
     */
    @Test
    void testRunningTotalWithMultipleItems() {
        sale.addItem(testItem1);
        sale.addItem(testItem2);
        assertEquals(25.0, sale.getRunningTotal(), "Running total should reflect the sum of item prices.");
    }

    /**
     * Tests adding the same item multiple times and verifying the total.
     */
    @Test
    void testAddSameItemMultipleTimes() {
        sale.addItem(testItem1);
        sale.addItem(testItem1);
        assertEquals(20.0, sale.getRunningTotal(), "Running total should reflect the price of the item added twice.");
    }

    /**
     * Tests processing a sale with insufficient payment.
     */
    @Test
    void testProcessSaleWithInsufficientPayment() {
        sale.addItem(testItem1);
        sale.addItem(testItem2);

        SaleSummaryDTO summary = sale.processSale(20.0);

        assertEquals(25.0, summary.getTotalPrice(), "Total price should match the sum of item prices.");
        assertEquals(20.0, summary.getAmountPaid(), "Amount paid should match the input.");
        assertEquals(-5.0, summary.getChange(), "Change should be negative for insufficient payment.");
    }

    /**
     * Tests adding an item with a high VAT rate and verifying the total VAT.
     */
    @Test
    void testAddItemWithHighVAT() {
        ItemDTO expensiveItem = new ItemDTO("Luxury Watch", "003", "High-end watch", 1000.0, 0.25);
        sale.addItem(expensiveItem);

        assertEquals(1000.0, sale.getRunningTotal(), "Running total should reflect the item's price.");
        assertEquals(250.0, sale.processSale(1000.0).getTotalVAT(), "Total VAT should be correctly calculated.");
    }
}