package it.uniroma2.dicii.bd.dao.MySQL;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;

import java.sql.Connection;
import java.util.List;

public class MySQLFilamentDao implements FilamentDao {

    @Override
    public void insertFilament(Filament filament) throws DaoException {

        MySQLConnectionManager manager = MySQLConnectionManager.getSingletonInstance();
        Connection conn = null;
    }

    @Override
    public void insertAllBoundaryPointPerFilament(Filament filament) throws DaoException {

    }

    @Override
    public GPoint getFilamentCentroidByID(Integer filamentID) throws DaoException {
        return null;
    }

    @Override
    public GPoint getFilamentCentroidByName(String filamentName) throws DaoException {
        return null;
    }

    @Override
    public GPoint getFilamentExtensionByID(Integer filamentID) throws DaoException {
        return null;
    }

    @Override
    public GPoint getFilamentExtensionByName(String filamentName) throws DaoException {
        return null;
    }

    @Override
    public Integer getCountFilamentSegmentByName(String filamentName) throws DaoException {
        return null;
    }

    @Override
    public Integer getCountFilamentSegmentByID(Integer filamentID) throws DaoException {
        return null;
    }

    @Override
    public List<Filament> getFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max, Integer limit, Integer offset) throws DaoException {
        return null;
    }

    @Override
    public List<Filament> getFilamentsByNumberOfSegments(Integer from, Integer to, Integer limit, Integer offset) throws DaoException {
        return null;
    }

    @Override
    public List<Filament> getFilamentInsideSquareRegion(GPoint center, Float side, Integer limit, Integer offset) throws DaoException {
        return null;
    }

    @Override
    public List<Filament> getFilamentInsideCircleRegion(GPoint center, Float radius, Integer limit, Integer offset) throws DaoException {
        return null;
    }

    @Override
    public List<GPoint> getFilamentBoundary(Filament filament) throws DaoException {
        return null;
    }

    @Override
    public Branch getFilamentSpine(Filament filament) throws DaoException {
        return null;
    }

    @Override
    public Integer getCountFilament() throws DaoException {
        return null;
    }

    @Override
    public Integer getCountFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max) throws DaoException {
        return null;
    }
}
