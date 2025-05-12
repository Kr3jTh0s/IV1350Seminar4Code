package src.main.java.processSale.model.dto;

/**
 * A Data Transfer Object (DTO) representing the time of a sale.
 * Encapsulates the timestamp of when the sale occurred.
 */
public class TimeOfSaleDTO {
    private final String timeStamp; // The timestamp of the sale

    /**
     * Creates a new instance representing the time of a sale.
     * 
     * @param timeStamp The date and time of the sale as a string.
     */
    public TimeOfSaleDTO(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Retrieves the timestamp of the sale.
     * 
     * @return The timestamp of the sale as a string.
     */
    public String getTimeStamp() {
        return timeStamp;
    }
}
