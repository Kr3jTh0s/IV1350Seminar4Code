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
     * Tests retrieving an item by its valid ID.
     *
     * @throws ItemNotFoundException if the item is not found (should not happen in
     *                               this test).
     */
    @Test
    void testGetItem() throws ItemNotFoundException {
        ItemDTO item = inventory.getItem("1");
        assertNotNull(item, "Valid item should be retrieved.");
        assertEquals("1", item.getID(), "Item ID should match the requested ID.");
    }

    /**
     * Tests retrieving an item with an invalid ID throws ItemNotFoundException.
     */
    @Test
    void testGetItemWithInvalidIDThrowsException() {
        assertThrows(ItemNotFoundException.class, () -> inventory.getItem("999"),
                "Retrieving an item with an invalid ID should throw ItemNotFoundException.");
    }

    /**
     * Tests retrieving an item with a null ID throws NullPointerException.
     */
    @Test
    void testGetItemWithNullIDThrowsException() {
        assertThrows(ItemNotFoundException.class, () -> inventory.getItem(null),
                "Retrieving an item with a null ID should throw NullPointerException.");
    }

    /**
     * Tests updating the inventory after a sale. This should not throw any
     * exceptions.
     */
    @Test
    void testUpdateInventory() {
        // Mocking a SaleSummaryDTO with dummy data
        HashMap<ItemDTO, Integer> boughtItems = new HashMap<>();
        boughtItems.put(new ItemDTO("Apple", "1", "Fresh red apple", new BigDecimal(10.0), new BigDecimal(0.12)), 2);
        boughtItems.put(new ItemDTO("Banana", "2", "Yellow banana", new BigDecimal(15.0), new BigDecimal(0.06)), 1);

        BoughtItemsDTO boughtItemsDTO = new BoughtItemsDTO(boughtItems);
        TimeOfSaleDTO timeOfSale = new TimeOfSaleDTO("2023-05-01_14:30");
        SaleSummaryDTO saleSummary = new SaleSummaryDTO(timeOfSale, boughtItemsDTO, null);

        assertDoesNotThrow(() -> inventory.updateInventory(saleSummary),
                "Updating inventory should not throw an exception.");
    }

    /**
     * Tests that updating inventory with a null SaleSummaryDTO does not throw an
     * exception.
     */
    @Test
    void testUpdateInventoryWithNullSummary() {
        assertDoesNotThrow(() -> inventory.updateInventory(null),
                "Updating inventory with null should not throw an exception.");
    }
}