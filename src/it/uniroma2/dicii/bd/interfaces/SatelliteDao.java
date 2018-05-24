package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Satellite;

import java.util.List;

public interface SatelliteDao {

    /**
     * Get all satellites.
     *
     * @return list of all satellites
     * @throws DaoException
     */
    List<Satellite> getSatellites() throws DaoException;

    /**
     * Insert satellite on db
     *
     * @param satellite
     * @throws DaoException
     */
    void insertSatellite(Satellite satellite) throws DaoException;

    /**
     * Delete satellite from db
     *
     * @param satellite
     * @throws DaoException
     */
    void deleteSatellite(Satellite satellite) throws DaoException;

}
