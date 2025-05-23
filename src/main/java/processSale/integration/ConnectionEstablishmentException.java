package src.main.java.processSale.integration;

/**
 * Thrown to indicate that a connection to an external system could not be
 * established. This is an unchecked exception (subclass of RuntimeException).
 */
public class ConnectionEstablishmentException extends RuntimeException {
    private String source;

    /**
     * Constructs a new ConnectionEstablishmentException with the specified detail
     * message and the source system.
     *
     * @param msg    The detail message explaining the reason for the exception.
     * @param source The source or system with which the connection could not be
     *               established.
     */
    public ConnectionEstablishmentException(String msg, String source) {
        super(msg);
        this.source = source;
    }

    /**
     * Returns the source or system with which the connection could not be
     * established.
     *
     * @return The source system as a string.
     */
    public String getSource() {
        return source;
    }
}
