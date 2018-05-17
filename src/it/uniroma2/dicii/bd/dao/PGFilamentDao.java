package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PGFilamentDao implements FilamentDao{

    @Override
    public void insert(Filament filament) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();

        PreparedStatement statement = null;
        Connection conn = null;

        try {

            final String sql = "INSERT INTO filament(idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast) values (?,?,?,?,?,?,?)";

            conn = manager.getConnectionFromConnectionPool();
            statement = conn.prepareStatement(sql);

            statement.setInt(1, filament.getIdfil());
            statement.setString(2, filament.getName());
            statement.setBigDecimal(3, filament.getTotalFlux());
            statement.setBigDecimal(4, filament.getMeanDensity());
            statement.setFloat(5, filament.getMeanTemperature());
            statement.setFloat(6, filament.getEllipticity());
            statement.setFloat(7, filament.getContrast());
            statement.executeUpdate();

            // Clean-up
            statement.close();
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {

                if (statement != null)
                    statement.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }

    @Override
    public void insertBoundary(Filament filament) throws DaoException {

        System.out.println("QUI!!");

        ConnectionManager manager = ConnectionManager.getSingletonInstance();

        PreparedStatement statement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            // start transaction
            conn.setAutoCommit(false);

            for (GPoint point : filament.getBoundary()) {

                final String sqlPoint = "INSERT INTO point(galactic_longitude, galactic_latitude) values (?,?)";
                statement = conn.prepareStatement(sqlPoint);
                statement.setFloat(1, point.getGlongitude());
                statement.setFloat(2, point.getGlatitude());
                statement.executeUpdate();

                final String sqlFilamentPoint = "INSERT INTO filament_boundary(filament, galactic_longitude, galactic_latitude) values (?,?,?)";
                statement = conn.prepareStatement(sqlFilamentPoint);
                statement.setInt(1, filament.getIdfil());
                statement.setFloat(2, point.getGlongitude());
                statement.setFloat(3, point.getGlatitude());
                statement.executeUpdate();

                System.out.println("Inserted");
            }

            // commit
            conn.commit();

            // Clean-up
            statement.close();
            conn.close();

        }  catch (SQLException e) {

            try {

                if (conn != null)
                    conn.rollback();

            } catch (SQLException ex) {

                throw new DaoException(ex.getMessage(), ex.getCause());
            }

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {

                if (statement != null)
                    statement.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }
}
