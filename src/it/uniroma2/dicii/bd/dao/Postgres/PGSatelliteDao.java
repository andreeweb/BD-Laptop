package it.uniroma2.dicii.bd.dao.Postgres;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.SatelliteDao;
import it.uniroma2.dicii.bd.model.Satellite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGSatelliteDao implements SatelliteDao {

    @Override
    public List<Satellite> getSatellites() throws DaoException {

        List<Satellite> list = new ArrayList<Satellite>();

        Statement stmt = null;
        Connection conn = null;

        try {

            PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
            conn = manager.getConnectionFromConnectionPool();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM satellite";

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                Satellite satellite = new Satellite(rs.getString("name"));
                satellite.setFirstObservation(rs.getDate("first_observation"));
                satellite.setLastObservation(rs.getDate("last_observation"));
                satellite.setAgency(rs.getString("agency"));

                list.add(satellite);
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
    public void insertSatellite(Satellite satellite) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            final String sql = "INSERT INTO satellite(name, first_observation, last_observation, agency) values (?,?,?,?)";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, satellite.getName());
            preparedStatement.setDate(2, satellite.getFirstObservation());
            preparedStatement.setDate(3, satellite.getLastObservation());
            preparedStatement.setString(4, satellite.getAgency());
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
    public void deleteSatellite(Satellite satellite) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            final String sql = "DELETE FROM satellite WHERE name='" + satellite.getName() + "'";

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
