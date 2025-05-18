package src.test.java.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.integration.Printer;
import src.main.java.processSale.model.dto.*;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Printer} class.
 */
class PrinterTest {
    private Printer printer;
    private TimeOfSaleDTO timeOfSale;
    private SaleSummaryDTO saleSummary;

    /**
     * Sets up a new Printer instance and test data before each test.
     */
    @BeforeEach
    void setUp() {
        printer = new Printer();
        timeOfSale = new TimeOfSaleDTO("2023-05-01_14:30");

        // Mocking a SaleSummaryDTO with dummy data
        HashMap<ItemDTO, Integer> boughtItems = new HashMap<>();
        boughtItems.put(new ItemDTO("Apple", "001", "Fresh red apple", new BigDecimal(10.0), new BigDecimal(0.12)), 2);
        boughtItems.put(new ItemDTO("Banana", "002", "Yellow banana", new BigDecimal(15.0), new BigDecimal(0.06)), 1);

        BoughtItemsDTO boughtItemsDTO = new BoughtItemsDTO(boughtItems);
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO(new BigDecimal(50.0), new BigDecimal(10.0),
                new BigDecimal(40.0), new BigDecimal(5.0));
        saleSummary = new SaleSummaryDTO(timeOfSale, boughtItemsDTO, paymentInfo);
    }

    /**
     * Tests creating a receipt with a valid time of sale.
     */
    @Test
    void testCreateReceipt() {
        assertDoesNotThrow(() -> printer.createReceipt(timeOfSale),
                "Creating a receipt should not throw an exception.");
    }

    /**
     * Tests creating a receipt with a null time of sale should throw
     * NullPointerException.
     */
    @Test
    void testCreateReceiptWithNullTimeOfSaleThrowsException() {
        assertThrows(NullPointerException.class, () -> printer.createReceipt(null),
                "Creating a receipt with null TimeOfSaleDTO should throw NullPointerException.");
    }

    /**
     * Tests printing a receipt after creating it.
     */
    @Test
    void testPrintReceipt() {
        printer.createReceipt(timeOfSale);
        assertDoesNotThrow(() -> printer.printReceipt(saleSummary),
                "Printing a receipt should not throw an exception.");
    }

    /**
     * Tests printing a receipt without creating it first should throw
     * IllegalStateException.
     */
    @Test
    void testPrintReceiptWithoutCreatingThrowsException() {
        assertThrows(IllegalStateException.class, () -> printer.printReceipt(saleSummary),
                "Printing without creating a receipt should throw IllegalStateException.");
    }

    /**
     * Tests printing a receipt with null SaleSummaryDTO should throw
     * NullPointerException.
     */
    @Test
    void testPrintReceiptWithNullSummaryThrowsException() {
        printer.createReceipt(timeOfSale);
        assertThrows(NullPointerException.class, () -> printer.printReceipt(null),
                "Printing with a null SaleSummaryDTO should throw NullPointerException.");
    }

    /**
     * Tests that printing a receipt with a SaleSummaryDTO with null fields does not
     * throw.
     */
    @Test
    void testPrintReceiptWithEmptySummary() {
        printer.createReceipt(timeOfSale);
        SaleSummaryDTO summaryWithNulls = new SaleSummaryDTO(null, null, null);
        assertDoesNotThrow(() -> printer.printReceipt(summaryWithNulls),
                "Printing with a SaleSummaryDTO with null fields should not throw an exception.");
    }
}