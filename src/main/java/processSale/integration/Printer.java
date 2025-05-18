package src.main.java.processSale.integration;

import src.main.java.processSale.model.dto.*;

/**
 * Represents a printer that handles the creation and printing of receipts.
 */
public class Printer {
    private Receipt currentReceipt; // The receipt being created or printed

    /**
     * Creates a new instance of the Printer.
     * Prints a message indicating that the printer has been initialized.
     */
    public Printer() {
        System.out.println("Printer initialized");
    }

    /**
     * Creates a new receipt for the current sale.
     *
     * @param timeOfSale The timestamp of the sale, encapsulated in a
     *                   {@link TimeOfSaleDTO}.
     * @throws NullPointerException if {@code timeOfSale} is {@code null}.
     */
    public void createReceipt(TimeOfSaleDTO timeOfSale) {
        if (timeOfSale == null) {
            throw new NullPointerException("TimeOfSaleDTO cannot be null when creating a receipt.");
        }
        currentReceipt = new Receipt(timeOfSale);
    }

    /**
     * Prints the receipt for the completed sale.
     *
     * @param saleSummaryDTO A {@link SaleSummaryDTO} containing the details of the
     *                       completed sale.
     * @throws IllegalStateException if no receipt has been created before printing.
     * @throws NullPointerException  if {@code saleSummaryDTO} is {@code null}.
     */
    public void printReceipt(SaleSummaryDTO saleSummaryDTO) {
        if (currentReceipt == null) {
            throw new IllegalStateException("No receipt has been created. Please create a receipt first.");
        }
        if (saleSummaryDTO == null) {
            throw new NullPointerException("SaleSummaryDTO cannot be null when printing a receipt.");
        }
        currentReceipt.printReceipt(saleSummaryDTO);
    }
}
