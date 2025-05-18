package src.main.java.processSale.model;

/**
 * Thrown to indicate that an item with the specified identifier could not be
 * found in the inventory system. This is a checked exception (subclass of
 * Exception).
 */
public class ItemNotFoundException extends Exception {
    private String idOfNotFoundItem;

    /**
     * Constructs a new ItemNotFoundException with the specified detail message and
     * item ID.
     *
     * @param msg    The detail message explaining the reason for the exception.
     * @param itemID The identifier of the item that was not found.
     */
    public ItemNotFoundException(String msg, String itemID) {
        super(msg);
        idOfNotFoundItem = itemID;
    }

    /**
     * Returns the identifier of the item that was not found.
     *
     * @return The item ID that could not be found in the inventory.
     */
    public String getItemNotFoundID() {
        return idOfNotFoundItem;
    }
}
