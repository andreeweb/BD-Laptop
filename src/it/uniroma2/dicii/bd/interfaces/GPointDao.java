package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.GPoint;

public interface GPointDao {

    /**
     * Insert GPoint on database
     *
     * @return array
     */
    void insertGPoint(GPoint point) throws DaoException;

}
