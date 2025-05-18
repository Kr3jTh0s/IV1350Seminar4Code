package src.test.java.model;

import org.junit.jupiter.api.Test;
import src.main.java.processSale.model.ItemNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ItemNotFoundException} class.
 */
class ItemNotFoundExceptionTest {

    /**
     * Tests that the exception message and item ID are set correctly.
     */
    @Test
    void testExceptionMessageAndItemID() {
        String message = "Item not found";
        String itemID = "ABC123";
        ItemNotFoundException exception = new ItemNotFoundException(message, itemID);

        assertEquals(message, exception.getMessage(), "Exception message should match the input message.");
        assertEquals(itemID, exception.getItemNotFoundID(), "Item ID should match the input item ID.");
    }

    /**
     * Tests that the exception can be thrown and caught.
     */
    @Test
    void testThrowAndCatchException() {
        assertThrows(ItemNotFoundException.class, () -> {
            throw new ItemNotFoundException("Not found", "XYZ789");
        }, "Should throw ItemNotFoundException.");
    }

    /**
     * Tests that the item ID can be null and is handled correctly.
     */
    @Test
    void testNullItemID() {
        ItemNotFoundException exception = new ItemNotFoundException("Missing item", null);
        assertNull(exception.getItemNotFoundID(), "Item ID should be null if passed as null.");
    }
}
