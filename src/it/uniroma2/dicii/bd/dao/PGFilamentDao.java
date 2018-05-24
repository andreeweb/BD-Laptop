package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;
import java.sql.*;
import java.util.LinkedList;

public class PGFilamentDao implements FilamentDao{

    @Override
    public void insertFilament(Filament filament) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            this._insertFilament(filament, conn);
            this._insertFilamentToolRelation(filament, conn);
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }

    @Override
    public void insertAllBoundaryPointPerFilament(Filament filament) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            GPointDao dao = DaoFactory.getSingletonInstance().getGPointDAO();

            conn = manager.getConnectionFromConnectionPool();

            for (GPoint point : filament.getBoundary()) {
                dao.insertGPoint(point);
                this._insertFilamentBoundaryPointRelation(filament, point, conn);
            }

            conn.close();

        } catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

    private void _insertFilament(Filament filament, Connection connection) throws DaoException {

        final String sql = "INSERT INTO filament(idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast) values (?,?,?,?,?,?,?) ON conflict (idfil) do nothing";
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, filament.getIdfil());
            preparedStatement.setString(2, filament.getName());
            preparedStatement.setBigDecimal(3, filament.getTotalFlux());
            preparedStatement.setBigDecimal(4, filament.getMeanDensity());
            preparedStatement.setFloat(5, filament.getMeanTemperature());
            preparedStatement.setFloat(6, filament.getEllipticity());
            preparedStatement.setFloat(7, filament.getContrast());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

    private void _insertFilamentBoundaryPointRelation(Filament filament, GPoint point, Connection connection) throws DaoException {

        final String sqlFilamentPoint = "INSERT INTO filament_boundary(filament, galactic_longitude, galactic_latitude) values (?,?,?) ON conflict (filament, galactic_longitude, galactic_latitude) do nothing";
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sqlFilamentPoint);

            preparedStatement.setInt(1, filament.getIdfil());
            preparedStatement.setDouble(2, point.getGlongitude());
            preparedStatement.setDouble(3, point.getGlatitude());

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch (SQLException e){

            throw new DaoException(e.getMessage(), e.getCause());

        }finally {

            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

    private void _insertFilamentToolRelation(Filament filament, Connection connection) throws DaoException {

        final String sqlFilamentPoint = "INSERT INTO tool_filament(nametool, filament) values (?,?) ON conflict (nametool, filament) do nothing";
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sqlFilamentPoint);

            preparedStatement.setString(1, filament.getTool().getName());
            preparedStatement.setInt(2, filament.getIdfil());

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch (SQLException e){

            throw new DaoException(e.getMessage(), e.getCause());

        }finally {

            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

}
