package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Star;

public interface StarDao {

    /**
     * Insert star on database
     *
     * @throws DaoException
     */
    void insertStar(Star star) throws DaoException;
}
