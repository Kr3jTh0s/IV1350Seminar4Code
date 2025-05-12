package src.main.java.processSale.model.dto;

import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) representing an item. It contains information 
 * about the item's name, ID, description, price, and VAT rate.
 */
public class ItemDTO {
    private final String name;        // The name of the item
    private final String ID;          // The unique identifier of the item
    private final String description; // A brief description of the item
    private final BigDecimal price;       // The price of the item
    private final BigDecimal VATRate;     // The VAT rate applicable to the item

    /**
     * Creates a new instance of ItemDTO with the specified attributes.
     * 
     * @param name        The name of the item.
     * @param ID          The unique identifier of the item.
     * @param description A brief description of the item.
     * @param price       The price of the item.
     * @param VATRate     The VAT rate applicable to the item.
     */
    public ItemDTO(String name, String ID, String description, BigDecimal price, BigDecimal VATRate) {
        this.name = name;
        this.ID = ID;
        this.description = description;
        this.price = price;
        this.VATRate = VATRate;
    }

    /**
     * Retrieves the name of the item.
     * 
     * @return The name of the item as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the unique identifier of the item.
     * 
     * @return The ID of the item as a string.
     */
    public String getID() {
        return ID;
    }

    /**
     * Retrieves the description of the item.
     * 
     * @return The description of the item as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the price of the item.
     * 
     * @return The price of the item as a double.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Retrieves the VAT rate applicable to the item.
     * 
     * @return The VAT rate of the item as a double.
     */
    public BigDecimal getVATRate() {
        return VATRate;
    }
}