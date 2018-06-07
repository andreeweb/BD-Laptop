package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.model.GPoint;

import java.sql.*;

public class PGGPointDao implements GPointDao{


    @Override
    public void insertGPoint(GPoint point) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            this._insertGPoint(point, conn);
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

    private void _insertGPoint(GPoint point, Connection connection) throws DaoException {

        final String sqlPoint = "INSERT INTO point(galactic_longitude, galactic_latitude) values (?,?) ON conflict (galactic_longitude, galactic_latitude) do nothing";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sqlPoint);

            preparedStatement.setDouble(1, point.getGlongitude());
            preparedStatement.setDouble(2, point.getGlatitude());

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
