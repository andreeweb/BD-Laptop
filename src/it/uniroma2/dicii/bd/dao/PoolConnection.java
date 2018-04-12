package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.ConnectionException;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Pool connection manager class
 *
 * @author  Andrea Cerra
 */
public class PoolConnection {

    private static PoolConnection instance = null;
    private static Map<Long, Connection> connections = new HashMap<>();

    private PoolConnection() {

    }

    public Connection getConnection(long key) throws ConnectionException {

        try {

            String maxConnection = Config.getSingletonInstance().getProperty("filamentDaoClass");
            Connection conn = null;

            // connection already created for this thread
            conn = connections.get(key);
            if (conn != null){
                return conn;
            }

            Class.forName(Config.getSingletonInstance().getProperty("dbdriver"));
            conn = DriverManager.getConnection(
                    Config.getSingletonInstance().getProperty("dburl"),
                    Config.getSingletonInstance().getProperty("dbuser"),
                    Config.getSingletonInstance().getProperty("dbpassword"));

            if (connections.size() == Integer.valueOf(maxConnection)){

                throw new ConnectionException("Max connection reached, see config file.");

            }else{

                connections.put(key, conn);
                return conn;

            }


        } catch (ClassNotFoundException | SQLException e) {

            throw new ConnectionException(e.getMessage(), e.getCause());

        }
    }

    /**
     * Return or inizialize the Config singleton istance
     *
     * @return reference to singleton istance
     */
    public synchronized static final PoolConnection getSingletonInstance(){

        if (PoolConnection.instance == null){
            PoolConnection.instance = new PoolConnection();
        }

        return instance;
    }
}
