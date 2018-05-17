package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.utils.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pool connection manager class
 *
 * @author  Andrea Cerra
 */
public class ConnectionManager {

    private static ConnectionManager instance = null;
    private BasicDataSource connectionPool;


    /**
     *
     */
    private ConnectionManager() throws URISyntaxException {

        String dbUrl = Config.getSingletonInstance().getProperty("dburl");

        connectionPool = new BasicDataSource();

        connectionPool.setUsername(Config.getSingletonInstance().getProperty("dbuser"));
        connectionPool.setPassword(Config.getSingletonInstance().getProperty("dbpassword"));

        connectionPool.setDriverClassName(Config.getSingletonInstance().getProperty("dbdriver"));
        connectionPool.setUrl(dbUrl);

        connectionPool.setInitialSize(15);
        connectionPool.setMaxTotal(100);

    }

    public Connection getConnectionFromConnectionPool() throws SQLException {

        return connectionPool.getConnection();
    }

    /**
     *
     */
    public static synchronized ConnectionManager getSingletonInstance() {

        if (ConnectionManager.instance == null){

            try {
                ConnectionManager.instance = new ConnectionManager();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return instance;
    }
}
