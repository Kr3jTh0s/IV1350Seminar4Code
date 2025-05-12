package src.main.java.processSale.integration;

import src.main.java.processSale.model.dto.SaleSummaryDTO;

/**
 * Represents the accounting system that handles the recording of sales.
 * This class is responsible for logging completed sales for accounting
 * purposes.
 */
public class Account {

    /**
     * Creates a new instance of the Account system. This constructor 
     * can be extended to initialize accounting-specific settings.
     */
    public Account() {
        System.out.println("Account system initialized.");
    }

    /**
     * Records a completed sale in the accounting system.
     * 
     * @param saleSummary A {@link SaleSummaryDTO} containing details of the
     *                    completed sale.
     */
    public void accountSale(SaleSummaryDTO saleSummary) {
        System.out.println("Sale recorded in the accounting system.");
    }
}
