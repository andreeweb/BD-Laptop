package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.interfaces.StarDao;
import it.uniroma2.dicii.bd.model.Star;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PGStarDao implements StarDao{

    @Override
    public void insertStar(Star star) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            GPointDao dao = DaoFactory.getSingletonInstance().getGPointDAO();
            dao.insertGPoint(star.getPosition());

            this._insertStar(star, conn);

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

    private void _insertStar(Star star, Connection connection) throws DaoException {

        final String sql = "INSERT INTO star(idstar, name_star, type, flux, galactic_longitude, galactic_latitude, nameTool) " +
                "values (?,?,?,?,?,?,?) ON conflict (idstar) " +
                "DO UPDATE SET name_star=excluded.name_star, type=excluded.type, " +
                "           flux=excluded.flux, galactic_longitude=excluded.galactic_longitude, " +
                "           galactic_latitude=excluded.galactic_latitude, nameTool=excluded.nameTool";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, star.getIdStar());
            preparedStatement.setString(2, star.getName());
            preparedStatement.setString(3, String.valueOf(star.getType()));
            preparedStatement.setBigDecimal(4, star.getFlux());
            preparedStatement.setDouble(5, star.getPosition().getGlongitude());
            preparedStatement.setDouble(6, star.getPosition().getGlatitude());
            preparedStatement.setString(7, star.getTool().getName());

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
}
