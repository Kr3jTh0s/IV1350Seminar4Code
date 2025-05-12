package src.main.java.processSale.integration;

import src.main.java.processSale.model.dto.*;

/**
 * Represents a printer that handles the creation and printing of receipts.
 */
public class Printer {
    private Receipt currentReceipt; // The receipt being created or printed

    /**
     * Creates a new instance of the Printer.
     * This constructor can be extended to initialize printer-specific settings.
     */
    public Printer() {
        System.out.println("Printer initialized");
    }

    /**
     * Creates a new receipt for the current sale.
     * 
     * @param timeOfSale The timestamp of the sale, encapsulated in a
     *                   {@link TimeOfSaleDTO}.
     */
    public void createReceipt(TimeOfSaleDTO timeOfSale) {
        currentReceipt = new Receipt(timeOfSale);
    }

    /**
     * Prints the receipt for the completed sale.
     * 
     * @param saleSummaryDTO A {@link SaleSummaryDTO} containing the details of the
     *                       completed sale.
     */
    public void printReceipt(SaleSummaryDTO saleSummaryDTO) {
        if (currentReceipt == null) {
            System.out.println("No receipt has been created. Please create a receipt first.");
            return;
        }
        if (saleSummaryDTO == null) {
            System.out.println("Sale summary is null.");
            return;
        }
        currentReceipt.printReceipt(saleSummaryDTO);
    }
}
