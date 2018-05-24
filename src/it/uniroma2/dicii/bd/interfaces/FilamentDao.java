package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;

import java.sql.Connection;
import java.util.LinkedList;

public interface FilamentDao {

    /**
     * Insert filament on database
     *
     * @return array
     */
    void insertFilament(Filament filament) throws DaoException;

    /**
     *
     * Insert all filament boundary point and their relation in db
     *
     * @param filament
     * @throws DaoException
     */
    void insertAllBoundaryPointPerFilament(Filament filament) throws DaoException;
}
