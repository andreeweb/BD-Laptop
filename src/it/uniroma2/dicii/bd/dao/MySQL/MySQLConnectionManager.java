package it.uniroma2.dicii.bd.dao.MySQL;

import it.uniroma2.dicii.bd.utils.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnectionManager {

    private static MySQLConnectionManager instance = null;
    private BasicDataSource connectionPool;


    /**
     *
     */
    private MySQLConnectionManager() throws URISyntaxException {

        String dbUrl = Config.getSingletonInstance().getProperty("MySQLdburl");

        connectionPool = new BasicDataSource();

        connectionPool.setUsername(Config.getSingletonInstance().getProperty("MySQLdbuser"));
        connectionPool.setPassword(Config.getSingletonInstance().getProperty("MySQLdbpassword"));

        connectionPool.setDriverClassName(Config.getSingletonInstance().getProperty("MySQLdbdriver"));
        connectionPool.setUrl(dbUrl);

        connectionPool.setInitialSize(30);
        connectionPool.setMaxTotal(100);

    }

    public Connection getConnectionFromConnectionPool() throws SQLException {

        return connectionPool.getConnection();
    }

    /**
     *
     */
    public static synchronized MySQLConnectionManager getSingletonInstance() {

        if (MySQLConnectionManager.instance == null){

            try {
                MySQLConnectionManager.instance = new MySQLConnectionManager();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return instance;
    }
}
