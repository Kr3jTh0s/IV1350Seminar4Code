package src.test.java.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.model.ItemList;
import src.main.java.processSale.model.dto.ItemDTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ItemList} class.
 */
class ItemListTest {
    private ItemList itemList;
    private ItemDTO testItem1;
    private ItemDTO testItem2;

    /**
     * Sets up a new ItemList instance and test items before each test.
     */
    @BeforeEach
    void setUp() {
        itemList = new ItemList();
        testItem1 = new ItemDTO("Apple", "001", "Fresh red apple", 10.0, 0.12);
        testItem2 = new ItemDTO("Banana", "002", "Yellow banana", 15.0, 0.06);
    }

    /**
     * Tests adding a new item to the list.
     */
    @Test
    void testAddNewItem() {
        itemList.addNewItem(testItem1);
        assertTrue(itemList.checkItem("001"), "Item should exist after being added.");
        assertEquals(1, itemList.getBoughtItemsDTO().getBoughtItems().get(testItem1), "Item quantity should be 1.");
    }

    /**
     * Tests increasing the quantity of an existing item in the list.
     */
    @Test
    void testIncreaseQuantity() {
        itemList.addNewItem(testItem1);
        itemList.increaseQuantity("001");
        assertEquals(2, itemList.getBoughtItemsDTO().getBoughtItems().get(testItem1), "Item quantity should be 2.");
    }

    /**
     * Tests checking if an item exists in the list.
     */
    @Test
    void testCheckItem() {
        assertFalse(itemList.checkItem("001"), "Item should not exist before being added.");
        itemList.addNewItem(testItem1);
        assertTrue(itemList.checkItem("001"), "Item should exist after being added.");
        assertFalse(itemList.checkItem("999"), "Non-existent item should not be found.");
    }

    /**
     * Tests retrieving an item by its ID.
     */
    @Test
    void testGetItem() {
        itemList.addNewItem(testItem1);
        ItemDTO retrievedItem = itemList.getItem("001");
        assertEquals(testItem1, retrievedItem, "Retrieved item should match the added item.");
        assertNull(itemList.getItem("999"), "Retrieving a non-existent item should return null.");
    }

    /**
     * Tests retrieving the BoughtItemsDTO object.
     */
    @Test
    void testGetBoughtItemsDTO() {
        itemList.addNewItem(testItem1);
        itemList.addNewItem(testItem2);
        assertEquals(2, itemList.getBoughtItemsDTO().getBoughtItems().size(), "BoughtItemsDTO should contain all items.");
        assertTrue(itemList.getBoughtItemsDTO().getBoughtItems().containsKey(testItem1), "BoughtItemsDTO should contain testItem1.");
        assertTrue(itemList.getBoughtItemsDTO().getBoughtItems().containsKey(testItem2), "BoughtItemsDTO should contain testItem2.");
    }

    /**
     * Tests adding the same item multiple times and verifying the total quantity.
     */
    @Test
    void testAddSameItemMultipleTimes() {
        itemList.addNewItem(testItem1);
        itemList.addNewItem(testItem1);
        assertEquals(1, itemList.getBoughtItemsDTO().getBoughtItems().get(testItem1), "Adding the same item again should not increase quantity.");
    }

    /**
     * Tests increasing the quantity of a non-existent item.
     */
    @Test
    void testIncreaseQuantityForNonExistentItem() {
        itemList.increaseQuantity("999");
        assertFalse(itemList.checkItem("999"), "Non-existent item should not be added when increasing quantity.");
    }

    /**
     * Tests the behavior when retrieving an empty BoughtItemsDTO.
     */
    @Test
    void testGetBoughtItemsDTOWhenEmpty() {
        assertEquals(0, itemList.getBoughtItemsDTO().getBoughtItems().size(), "BoughtItemsDTO should be empty initially.");
    }
}