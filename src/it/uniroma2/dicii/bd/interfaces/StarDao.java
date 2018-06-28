package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.model.Star;

import java.util.List;

public interface StarDao {

    /**
     * Insert star on database
     *
     * @throws DaoException
     */
    void insertStar(Star star) throws DaoException;

    /**
     *
     * @param center
     * @param side1
     * @param side2
     * @return
     * @throws DaoException
     */
    List<Star> getStarsInsideRectRegion(GPoint center, Float side1, Float side2) throws DaoException;

    /**
     *
     * @param filamentID
     * @return
     * @throws DaoException
     */
    List<Star> getStarsInsideFilamentByID(Integer filamentID) throws DaoException;

    /**
     *
     * @param starList
     * @return
     * @throws DaoException
     */
    List<Star> areInsideFilament(List<Star> starList) throws DaoException;
}
