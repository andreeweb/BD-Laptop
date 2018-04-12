package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Filament;

import java.sql.Connection;

public interface FilamentDao {

    /**
     * Insert filament on database
     *
     * @return array
     */
    void insert(Filament filament, Connection conn) throws DaoException;

}
