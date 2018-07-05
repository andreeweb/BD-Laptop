package it.uniroma2.dicii.bd.dao.Postgres;

import it.uniroma2.dicii.bd.enumeration.UserRole;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.UserDao;
import it.uniroma2.dicii.bd.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
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
            user.setUserID(rs.getInt("user_id"));
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

    @Override
    public List<User> getUsers() throws DaoException{

        List<User> list = new ArrayList<User>();

        Statement stmt = null;
        Connection conn = null;

        try {

            PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
            conn = manager.getConnectionFromConnectionPool();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM system_user WHERE role='USER'";

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                User user = new User();
                user.setUserID(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setUsername(rs.getString("username"));
                user.setUserRole(UserRole.valueOf(rs.getString("role")));
                user.setEmail(rs.getString("email"));

                list.add(user);
            }

            // Clean-up
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());

        } finally {

            try {

                if (stmt != null)
                    stmt.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }

        return list;

    }

    @Override
    public void insertUser(User user) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            final String sql = "INSERT INTO system_user(name, surname, username, password, email, role) values (?,?,?,?,?,?)";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getUserRole().toString());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }

    @Override
    public void deleteUser(User user) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            final String sql = "DELETE FROM system_user WHERE user_id=" + user.getUserID();

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }

}
