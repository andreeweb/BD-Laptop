package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.User;

import java.util.List;

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

    /**
     * Get all user.
     *
     * @return list of all user
     * @throws DaoException
     */
     List<User> getUsers() throws DaoException;

    /**
     * Insert user on db
     *
     * @param user
     * @throws DaoException
     */
    void insertUser(User user) throws DaoException;

    /**
     * Delete user from db
     *
     * @param user
     * @throws DaoException
     */
    void deleteUser(User user) throws DaoException;
}
