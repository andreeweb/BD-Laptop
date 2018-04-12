package it.uniroma2.dicii.bd.exception;

/**
 * Connection Exception
 *
 * @author Andrea Cerra
 */

public class ConnectionException extends Exception{

    /**
     *
     * @param message Exception message
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     *
     * @param message Exception message
     * @param cause Throwable cause
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause Throwable cause
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }
}