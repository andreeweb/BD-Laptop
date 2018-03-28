package it.uniroma2.dicii.bd.exception;

/**
 * DAO Exception for UserDao
 *
 * @author Andrea Cerra
 */

public class DaoException extends Exception{

    /**
     *
     * @param message Exception message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     *
     * @param message Exception message
     * @param cause Throwable cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause Throwable cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
