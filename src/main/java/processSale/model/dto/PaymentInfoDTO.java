package src.main.java.processSale.model.dto;

import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) that holds information about a payment.
 * It includes the amount paid by the customer, the change to be given back,
 * the total price of the sale, and the total VAT.
 */
public class PaymentInfoDTO {
    private final BigDecimal amountPaid;       // The total amount paid by the customer
    private final BigDecimal changeToGiveBack; // The change to return to the customer
    private final BigDecimal totalPrice;       // The total price of the sale (including VAT)
    private final BigDecimal totalVAT;         // The total VAT for the sale

    /**
     * Creates a new instance of PaymentInfoDTO.
     * 
     * @param amountPaid The total amount paid by the customer.
     * @param change     The change to be given back to the customer.
     * @param totalPrice The total price of the sale (including VAT).
     * @param totalVAT   The total VAT for the sale.
     */
    public PaymentInfoDTO(BigDecimal amountPaid, BigDecimal change, BigDecimal totalPrice, BigDecimal totalVAT) {
        this.amountPaid = amountPaid;
        this.changeToGiveBack = change;
        this.totalPrice = totalPrice;
        this.totalVAT = totalVAT;
    }

    /**
     * Retrieves the total amount paid by the customer.
     * 
     * @return The total amount paid as a double.
     */
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    /**
     * Retrieves the change to be given back to the customer.
     * 
     * @return The change amount as a double.
     */
    public BigDecimal getChangeToGiveBack() {
        return changeToGiveBack;
    }

    /**
     * Retrieves the total price of the sale, including VAT.
     * 
     * @return The total price as a double.
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Retrieves the total VAT for the sale.
     * 
     * @return The total VAT as a double.
     */
    public BigDecimal getTotalVAT() {
        return totalVAT;
    }
}
