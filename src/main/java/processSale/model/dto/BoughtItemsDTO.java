package src.main.java.processSale.model.dto;

import java.util.HashMap;

/**
 * A Data Transfer Object (DTO) representing the items purchased in a sale.
 * It encapsulates a mapping of items to their respective quantities.
 */
public class BoughtItemsDTO {
    private final HashMap<ItemDTO, Integer> boughtItems; // Maps items to their quantities

    /**
     * Creates a new instance of BoughtItemsDTO.
     * 
     * @param boughtItemsList A HashMap where the keys are {@link ItemDTO} objects
     *                        and the values are their quantities.
     */
    public BoughtItemsDTO(HashMap<ItemDTO, Integer> boughtItemsList) {
        this.boughtItems = new HashMap<>(boughtItemsList);
    }

    /**
     * Retrieves the items purchased in the sale along with their quantities.
     * 
     * @return A HashMap where the keys are {@link ItemDTO} objects and the values
     *         are their quantities.
     */
    public HashMap<ItemDTO, Integer> getBoughtItems() {
        return new HashMap<>(boughtItems);
    }
}
