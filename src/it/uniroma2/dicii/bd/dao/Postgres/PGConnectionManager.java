package it.uniroma2.dicii.bd.dao.Postgres;

import it.uniroma2.dicii.bd.utils.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pool connection manager class
 *
 * @author  Andrea Cerra
 */
public class PGConnectionManager {

    private static PGConnectionManager instance = null;
    private BasicDataSource connectionPool;


    /**
     *
     */
    private PGConnectionManager() throws URISyntaxException {

        String dbUrl = Config.getSingletonInstance().getProperty("PGdburl");

        connectionPool = new BasicDataSource();

        connectionPool.setUsername(Config.getSingletonInstance().getProperty("PGdbuser"));
        connectionPool.setPassword(Config.getSingletonInstance().getProperty("PGdbpassword"));

        connectionPool.setDriverClassName(Config.getSingletonInstance().getProperty("PGdbdriver"));
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
    public static synchronized PGConnectionManager getSingletonInstance() {

        if (PGConnectionManager.instance == null){

            try {
                PGConnectionManager.instance = new PGConnectionManager();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return instance;
    }
}
