package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.enumeration.UserRole;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.UserDao;
import it.uniroma2.dicii.bd.model.User;

import java.sql.*;

/**
 * Concrete implementation of UserDao interfaces
 * this implementation works with PostgreSQL database.
 *
 * @author Andrea Cerra
 *
 */

public class PGUserDao implements UserDao {

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DaoException {

        Statement stm = null;
        Connection conn = null;
        User user = null;

        try {

            ConnectionManager manager = ConnectionManager.getSingletonInstance();
            conn = manager.getConnectionFromConnectionPool();

            stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM system_user where username = '" + username + "' AND password = '" + password + "';";

            ResultSet rs = stm.executeQuery(sql);

            if (!rs.first())
                throw new DaoException("User not found");

            // ops..
            boolean moreThanOne = rs.first() && rs.next();
            if (moreThanOne)
                throw new DaoException("Multiple user in DB with same credentials");

            rs.first();

            user = new User();
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setUsername(rs.getString("username"));
            user.setPassword("");
            user.setEmail(rs.getString("email"));
            user.setUserRole(UserRole.valueOf(rs.getString("role")));

            rs.close();
            stm.close();
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {

                if (stm != null)
                    stm.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

        return user;
    }

}
