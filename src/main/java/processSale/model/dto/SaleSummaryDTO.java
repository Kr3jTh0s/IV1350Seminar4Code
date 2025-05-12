package src.main.java.processSale.model.dto;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * A Data Transfer Object (DTO) that summarizes the details of a completed sale.
 * It includes the time of the sale, purchased items, and payment information.
 */
public class SaleSummaryDTO {
    private final TimeOfSaleDTO timeOfSale;   // The timestamp of the sale
    private final BoughtItemsDTO boughtItems; // The items purchased in the sale
    private final PaymentInfoDTO paymentInfo; // Payment details for the sale

    /**
     * Creates a new instance of SaleSummaryDTO.
     * 
     * @param timeOfSale  The timestamp of the sale.
     * @param boughtItems The items purchased in the sale.
     * @param paymentInfo The payment details for the sale.
     */
    public SaleSummaryDTO(TimeOfSaleDTO timeOfSale, BoughtItemsDTO boughtItems, PaymentInfoDTO paymentInfo) {
        this.timeOfSale = timeOfSale;
        this.boughtItems = boughtItems;
        this.paymentInfo = paymentInfo;
    }

    /**
     * Retrieves the timestamp of the sale.
     * 
     * @return The timestamp of the sale as a string.
     */
    public String getTimeOfSale() {
        return timeOfSale.getTimeStamp();
    }

    /**
     * Retrieves the items purchased in the sale along with their quantities.
     * 
     * @return A HashMap where the keys are {@link ItemDTO} objects and the values
     *         are their quantities.
     */
    public HashMap<ItemDTO, Integer> getBoughtItems() {
        return boughtItems.getBoughtItems();
    }

    /**
     * Retrieves the total amount paid by the customer.
     * 
     * @return The amount paid as a double.
     */
    public BigDecimal getAmountPaid() {
        return paymentInfo.getAmountPaid();
    }

    /**
     * Retrieves the change to give back to the customer.
     * 
     * @return The change amount as a double.
     */
    public BigDecimal getChange() {
        return paymentInfo.getChangeToGiveBack();
    }

    /**
     * Retrieves the total price of the sale, including VAT.
     * 
     * @return The total price as a double.
     */
    public BigDecimal getTotalPrice() {
        return paymentInfo.getTotalPrice();
    }

    /**
     * Retrieves the total VAT for the sale.
     * 
     * @return The total VAT as a double.
     */
    public BigDecimal getTotalVAT() {
        return paymentInfo.getTotalVAT();
    }
}
