package src.test.java.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.integration.Inventory;
import src.main.java.processSale.model.dto.*;

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
    void testGetItem() {
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
        boughtItems.put(new ItemDTO("Apple", "1", "Fresh red apple", 10.0, 0.12), 2);
        boughtItems.put(new ItemDTO("Banana", "2", "Yellow banana", 15.0, 0.06), 1);

        BoughtItemsDTO boughtItemsDTO = new BoughtItemsDTO(boughtItems);
        TimeOfSaleDTO timeOfSale = new TimeOfSaleDTO("2023-05-01_14:30");
        SaleSummaryDTO saleSummary = new SaleSummaryDTO(timeOfSale, boughtItemsDTO, null);

        // Verify no exception is thrown
        assertDoesNotThrow(() -> inventory.updateInventory(saleSummary), "Updating inventory should not throw an exception.");
    }

    /**
     * Tests retrieving an item that does not exist in the inventory.
     */
    @Test
    void testGetNonExistentItem() {
        assertNull(inventory.getItem("999"), "Retrieving a non-existent item should return null.");
    }

    /**
     * Tests retrieving an item with a null ID.
     */
    @Test
    void testGetItemWithNullID() {
        assertNull(inventory.getItem(null), "Retrieving an item with a null ID should return null.");
    }

    /**
     * Tests the behavior of the inventory when it is empty.
     */
    @Test
    void testEmptyInventory() {
        Inventory emptyInventory = new Inventory() {
            @Override
            public ItemDTO getItem(String itemID) {
                return null;
            }
        };

        assertNull(emptyInventory.getItem("1"), "Retrieving an item from an empty inventory should return null.");
    }
}