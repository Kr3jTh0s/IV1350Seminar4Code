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
     * @throws NullPointerException if {@code timeOfSale} is {@code null}.
     */
    public Receipt(TimeOfSaleDTO timeOfSale) {
        if (timeOfSale == null) {
            throw new NullPointerException("TimeOfSaleDTO cannot be null.");
        }
        this.timeOfSale = timeOfSale.getTimeStamp();
    }

    /**
     * Prints the receipt for the completed sale to the console.
     *
     * @param saleSummary A {@link SaleSummaryDTO} containing the details of the
     *                    completed sale.
     * @throws NullPointerException if {@code saleSummary} is {@code null}.
     */
    public void printReceipt(SaleSummaryDTO saleSummary) {
        if (saleSummary == null) {
            throw new NullPointerException("SaleSummaryDTO cannot be null.");
        }
        this.saleSummary = saleSummary;

        // Build the list of purchased items
        HashMap<ItemDTO, Integer> boughtItems = saleSummary.getBoughtItems();
        StringBuilder items = new StringBuilder();
        boughtItems.forEach((itemDTO, quantity) -> {
            items.append(String.format("%s %d x %.2f = %.2f SEK%n",
                    itemDTO.getName(),
                    quantity,
                    itemDTO.getPrice(),
                    itemDTO.getPrice().multiply(BigDecimal.valueOf(quantity))));
        });

        // Print the receipt
        System.out.println("------------------ Begin receipt -------------------");
        System.out.println("Time of Sale: " + timeOfSale);
        System.out.println();
        System.out.print(items);
        System.out.printf("Total: %.2f SEK%n", saleSummary.getTotalPrice());
        System.out.printf("VAT: %.2f SEK%n", saleSummary.getTotalVAT());
        System.out.println();
        System.out.printf("Cash: %.2f SEK%n", saleSummary.getAmountPaid());
        System.out.printf("Change: %.2f SEK%n", saleSummary.getChange());
        System.out.println("------------------ End receipt ---------------------");
    }
}
