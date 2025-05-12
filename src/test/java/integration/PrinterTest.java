package src.test.java.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.integration.Printer;
import src.main.java.processSale.model.dto.*;

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
        boughtItems.put(new ItemDTO("Apple", "001", "Fresh red apple", 10.0, 0.12), 2);
        boughtItems.put(new ItemDTO("Banana", "002", "Yellow banana", 15.0, 0.06), 1);

        BoughtItemsDTO boughtItemsDTO = new BoughtItemsDTO(boughtItems);
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO(50.0, 10.0, 40.0, 5.0);
        saleSummary = new SaleSummaryDTO(timeOfSale, boughtItemsDTO, paymentInfo);
    }

    /**
     * Tests creating a receipt with a valid time of sale.
     */
    @Test
    void testCreateReceipt() {
        assertDoesNotThrow(() -> printer.createReceipt(timeOfSale), "Creating a receipt should not throw an exception.");
    }

    /**
     * Tests printing a receipt after creating it.
     */
    @Test
    void testPrintReceipt() {
        printer.createReceipt(timeOfSale);
        assertDoesNotThrow(() -> printer.printReceipt(saleSummary), "Printing a receipt should not throw an exception.");
    }

    /**
     * Tests printing a receipt without creating it first.
     */
    @Test
    void testPrintReceiptWithoutCreating() {
        assertDoesNotThrow(() -> printer.printReceipt(saleSummary), "Printing without creating a receipt should not throw an exception.");
    }

    /**
     * Tests creating multiple receipts consecutively.
     */
    @Test
    void testCreateMultipleReceipts() {
        TimeOfSaleDTO newTimeOfSale = new TimeOfSaleDTO("2023-05-02_10:00");
        assertDoesNotThrow(() -> printer.createReceipt(timeOfSale), "Creating the first receipt should not throw an exception.");
        assertDoesNotThrow(() -> printer.createReceipt(newTimeOfSale), "Creating a second receipt should not throw an exception.");
    }

    /**
     * Tests printing a receipt with null SaleSummaryDTO.
     */
    @Test
    void testPrintReceiptWithNullSummary() {
        printer.createReceipt(timeOfSale);
        assertDoesNotThrow(() -> printer.printReceipt(null), "Printing with a null SaleSummaryDTO should not throw an exception.");
    }
}