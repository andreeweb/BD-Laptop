package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.utils.Config;

import java.sql.*;

public class PGFilamentDao implements FilamentDao{


    @Override
    public void insert(Filament filament, Connection conn) throws DaoException {


        PreparedStatement statement = null;

        try {

            final String sql = "INSERT INTO filament(idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast) values (?,?,?,?,?,?,?)";

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

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {

                if (statement != null)
                    statement.close();

            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }
}
