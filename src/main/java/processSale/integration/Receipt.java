package src.main.java.processSale.integration;

import java.math.BigDecimal;
import java.util.HashMap;

import src.main.java.processSale.model.dto.*;

/**
 * Represents a receipt for a completed sale. It includes details such as the
 * time of sale, purchased items, total price, VAT, and payment information.
 */
class Receipt {
    private final String timeOfSale;    // The timestamp of the sale
    private SaleSummaryDTO saleSummary; // Summary of the completed sale

    /**
     * Creates a new instance of Receipt with the specified time of sale.
     * 
     * @param timeOfSale A {@link TimeOfSaleDTO} containing the timestamp of the
     *                   sale.
     */
    public Receipt(TimeOfSaleDTO timeOfSale) {
        this.timeOfSale = timeOfSale.getTimeStamp();
    }

    /**
     * Prints the receipt for the completed sale.
     * 
     * @param saleSummary A {@link SaleSummaryDTO} containing the details of the
     *                    completed sale.
     */
    public void printReceipt(SaleSummaryDTO saleSummary) {
        this.saleSummary = saleSummary;

        // Build the list of purchased items
        HashMap<ItemDTO, Integer> boughtItems = saleSummary.getBoughtItems();
        StringBuilder items = new StringBuilder();
        boughtItems.forEach((itemDTO, quantity) -> {
            items.append(String.format("%s %d x %.2f = " + itemDTO.getPrice().multiply(BigDecimal.valueOf(quantity)).toString() + " SEK%n",
                    itemDTO.getName(),
                    quantity,
                    itemDTO.getPrice()));
        });

        // Print the receipt
        System.out.println("------------------ Begin receipt -------------------");
        System.out.println("Time of Sale: " + timeOfSale);
        System.out.println();
        System.out.println(items.toString());
        System.out.printf("Total: %.2f SEK%n", saleSummary.getTotalPrice());
        System.out.printf("VAT: %.2f SEK%n", saleSummary.getTotalVAT());
        System.out.println();
        System.out.printf("Cash: %.2f SEK%n", saleSummary.getAmountPaid());
        System.out.printf("Change: %.2f SEK%n", saleSummary.getChange());
        System.out.println("------------------ End receipt ---------------------");
    }
}
