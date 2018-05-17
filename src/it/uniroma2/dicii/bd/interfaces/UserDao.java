package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.User;

/**
 * Interface DAO for entity User
 *
 * @author Andrea Cerra
 */

public interface UserDao {

    /**
     * Get User by username and password from database.
     *
     * @param username string containing the username
     * @param password string containing the password
     * @return instance of User
     * @throws DaoException if search fail or an error occurs.
     */
    User getUserByUsernameAndPassword(String username, String password) throws DaoException;

}
