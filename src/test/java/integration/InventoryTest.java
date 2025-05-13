package src.test.java.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.integration.Inventory;
import src.main.java.processSale.model.ItemNotFoundException;
import src.main.java.processSale.model.dto.*;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Inventory} class.
 */
class InventoryTest {
    private Inventory inventory;

    /**
     * Sets up a new Inventory instance before each test.
     */
    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }


    /**
     * Tests retrieving an item by its ID.
     */
    @Test
    void testGetItem() throws ItemNotFoundException {
        ItemDTO item = inventory.getItem("1");
        assertNotNull(item, "Valid item should be retrieved.");
        assertEquals("1", item.getID(), "Item ID should match the requested ID.");
    }

    /**
     * Tests updating the inventory after a sale.
     */
    @Test
    void testUpdateInventory() {
        // Mocking a SaleSummaryDTO with dummy data
        HashMap<ItemDTO, Integer> boughtItems = new HashMap<>();
        boughtItems.put(new ItemDTO("Apple", "1", "Fresh red apple", new BigDecimal(10.0),new BigDecimal(0.12)), 2);
        boughtItems.put(new ItemDTO("Banana", "2", "Yellow banana", new BigDecimal(15.0),new BigDecimal(0.06)), 1);

        BoughtItemsDTO boughtItemsDTO = new BoughtItemsDTO(boughtItems);
        TimeOfSaleDTO timeOfSale = new TimeOfSaleDTO("2023-05-01_14:30");
        SaleSummaryDTO saleSummary = new SaleSummaryDTO(timeOfSale, boughtItemsDTO, null);

        // Verify no exception is thrown
        assertDoesNotThrow(() -> inventory.updateInventory(saleSummary), "Updating inventory should not throw an exception.");
    }
}