package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.model.Star;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface FilamentDao {

    /**
     * Insert filament on database
     *
     * @throws DaoException
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

    /**
     *
     * Return filament centroid
     *
     * @param filamentID
     * @return centroid point
     * @throws DaoException filament not found
     */
    GPoint getFilamentCentroidByID(Integer filamentID) throws DaoException;

    /**
     *
     * Return filament centroid
     *
     * @param filamentName
     * @return centroid point
     * @throws DaoException filament not found
     */
    GPoint getFilamentCentroidByName(String filamentName) throws DaoException;

    /**
     *
     * Get filament extension
     *
     * @param filamentID
     * @return
     * @throws DaoException
     */
    GPoint getFilamentExtensionByID(Integer filamentID) throws DaoException;

    /**
     *
     * Get filament extension
     *
     * @param filamentName
     * @return
     * @throws DaoException
     */
    GPoint getFilamentExtensionByName(String filamentName) throws DaoException;

    /**
     *
     * Get number of filament's segment
     *
     * @param filamentName
     * @return
     * @throws DaoException
     */
    Integer getCountFilamentSegmentByName(String filamentName) throws DaoException;

    /**
     *
     * Get number of filament's segment
     *
     * @param filamentID
     * @return
     * @throws DaoException
     */
    Integer getCountFilamentSegmentByID(Integer filamentID) throws DaoException;

    /**
     *
     * Get filaments by Luminance and Ellipticity
     *
     * @param percentageLuminance
     * @param ellipticity_min
     * @param ellipticity_max
     * @param limit
     * @param offset
     * @return filaments
     * @throws DaoException
     */
    List<Filament> getFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max, Integer limit, Integer offset) throws DaoException;


    /**
     *
     * * Get filaments by number of segments
     *
     * @param from
     * @param to
     * @param limit
     * @param offset
     * @return filaments
     * @throws DaoException
     */
    List<Filament> getFilamentsByNumberOfSegments(Integer from, Integer to, Integer limit, Integer offset) throws DaoException;

    /**
     *
     * Get filaments inside square region
     *
     * @param side
     * @param center
     * @return
     * @throws DaoException
     */
    List<Filament> getFilamentInsideSquareRegion(GPoint center, Float side, Integer limit, Integer offset) throws DaoException;

    /**
     *
     * Get filaments inside circle region
     *
     * @param radius
     * @param center
     * @return
     * @throws DaoException
     */
    List<Filament> getFilamentInsideCircleRegion(GPoint center, Float radius, Integer limit, Integer offset) throws DaoException;

    /**
     *
     * @param filament
     * @return
     * @throws DaoException
     */
    List<GPoint> getFilamentBoundary(Filament filament) throws DaoException;

    /**
     *
     * @param filament
     * @return
     * @throws DaoException
     */
    Branch getFilamentSpine(Filament filament) throws DaoException;

    /**
     *
     * @return
     * @throws DaoException
     */
    Integer getCountFilament() throws DaoException;

    /**
     *
     * @param percentageLuminance
     * @param ellipticity_min
     * @param ellipticity_max
     * @return
     * @throws DaoException
     */
    Integer getCountFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max) throws DaoException;

}
