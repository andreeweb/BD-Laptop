package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.FilamentBean;
import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchController {

    // query per REQ-FN-5

    /**
     *
     * REQ-FN-5, filament centroid
     *
     * @param name filament
     * @return centroid latitude and longitude
     * @throws DaoException not found
     */
    public GPointBean getFilamentCentroidByName(String name) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        GPoint data = dao.getFilamentCentroidByName(name);
        return new GPointBean(data.getGlongitude(), data.getGlatitude());
    }

    /**
     *
     * REQ-FN-5, filament centroid
     *
     * @param filamentID filament
     * @return centroid latitude and longitude
     * @throws DaoException not found
     */
    public GPointBean getFilamentCentroidByID(Integer filamentID) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        GPoint data = dao.getFilamentCentroidByID(filamentID);
        return new GPointBean(data.getGlongitude(), data.getGlatitude());
    }

    /**
     *
     * REQ-FN-5, filament extension
     *
     * @param name filament
     * @return extension latitude and longitude
     * @throws DaoException not found
     */
    public GPointBean getFilamentExtensionByName(String name) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        GPoint data = dao.getFilamentExtensionByName(name);
        return new GPointBean(data.getGlongitude(), data.getGlatitude());

    }

    /**
     *
     * REQ-FN-5, filament extension
     *
     * @param filamentID filament
     * @return extension latitude and longitude
     * @throws DaoException not found
     */
    public GPointBean getFilamentExtensionByID(Integer filamentID) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        GPoint data = dao.getFilamentExtensionByID(filamentID);
        return new GPointBean(data.getGlongitude(), data.getGlatitude());
    }

    /**
     *
     * REQ-FN-5, number of segment
     *
     * @param name filament
     * @return number of segment
     * @throws DaoException not found
     */
    public Integer getNumberOfBranchByName(String name) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        return dao.getCountFilamentSegmentByName(name);

    }

    /**
     *
     * REQ-FN-5, number of segment
     *
     * @param filamentID filament
     * @return number of segment
     * @throws DaoException not found
     */
    public Integer getNumberOfBranchByID(Integer filamentID) throws DaoException {


            FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

            return dao.getCountFilamentSegmentByID(filamentID);

    }

    // query per REQ-FN-6

    /**
     *
     * REQ-FN-6, search by:
     *
     * @param percentageLuminance
     * @param ellipticity_min
     * @param ellipticity_max
     * @param limit
     * @param offset
     * @return
     * @throws DaoException
     */
    public List<FilamentBean> getFilamentsByLuminanceAndEllipticityWithLimit(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max, Integer limit, Integer offset) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
        List<Filament> filamentList = dao.getFilamentsByLuminanceAndEllipticity(percentageLuminance, ellipticity_min, ellipticity_max, limit, offset);

        List<FilamentBean> filamentBeans = new ArrayList<>();

        for (Filament filament : filamentList){

            FilamentBean filamentBean = new FilamentBean(filament.getIdfil());
            filamentBean.setName(filament.getName());
            filamentBean.setTotalFlux(filament.getTotalFlux());
            filamentBean.setMeanDensity(filament.getMeanDensity());
            filamentBean.setMeanTemperature(filament.getMeanTemperature());
            filamentBean.setEllipticity(filament.getEllipticity());
            filamentBean.setContrast(filament.getContrast());

            filamentBeans.add(filamentBean);
        }

        return filamentBeans;
    }

    // query per REQ-FN-7

    /**
     *
     * @param from
     * @param to
     * @param limit
     * @param offset
     * @return
     * @throws DaoException
     */
    public List<FilamentBean> getFilamentsByNumberOfSegments(Integer from, Integer to, Integer limit, Integer offset) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
        List<Filament> filamentList = dao.getFilamentsByNumberOfSegments(from, to, limit, offset);

        List<FilamentBean> filamentBeans = new ArrayList<>();

        for (Filament filament : filamentList){

            FilamentBean filamentBean = new FilamentBean(filament.getIdfil());
            filamentBean.setName(filament.getName());
            filamentBean.setTotalFlux(filament.getTotalFlux());
            filamentBean.setMeanDensity(filament.getMeanDensity());
            filamentBean.setMeanTemperature(filament.getMeanTemperature());
            filamentBean.setEllipticity(filament.getEllipticity());
            filamentBean.setContrast(filament.getContrast());

            filamentBeans.add(filamentBean);
        }

        return filamentBeans;
    }

    // query per REQ-FN-8

    /**
     *
     * @param regionType
     * @param centerBean
     * @param sideSize
     * @param limit
     * @param offset
     * @return
     * @throws DaoException
     */
    public List<FilamentBean> getFilamentInsideRegion(String regionType, GPointBean centerBean, Float sideSize, Integer limit, Integer offset) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        List<FilamentBean> filamentBeans = new ArrayList<>();

        if (regionType.equals("Square")){

            GPoint gPointCenter = new GPoint(centerBean.getGlongitude(), centerBean.getGlatitude());
            List<Filament> filamentList = dao.getFilamentInsideSquareRegion(gPointCenter, sideSize, limit, offset);

            for (Filament filament : filamentList){

                FilamentBean filamentBean = new FilamentBean(filament.getIdfil());
                filamentBean.setName(filament.getName());
                filamentBean.setTotalFlux(filament.getTotalFlux());
                filamentBean.setMeanDensity(filament.getMeanDensity());
                filamentBean.setMeanTemperature(filament.getMeanTemperature());
                filamentBean.setEllipticity(filament.getEllipticity());
                filamentBean.setContrast(filament.getContrast());

                filamentBeans.add(filamentBean);
            }

        }else if (regionType.equals("Circle")){

            GPoint gPointCenter = new GPoint(centerBean.getGlongitude(), centerBean.getGlatitude());
            List<Filament> filamentList = dao.getFilamentInsideCircleRegion(gPointCenter, sideSize, limit, offset);

            for (Filament filament : filamentList){

                FilamentBean filamentBean = new FilamentBean(filament.getIdfil());
                filamentBean.setName(filament.getName());
                filamentBean.setTotalFlux(filament.getTotalFlux());
                filamentBean.setMeanDensity(filament.getMeanDensity());
                filamentBean.setMeanTemperature(filament.getMeanTemperature());
                filamentBean.setEllipticity(filament.getEllipticity());
                filamentBean.setContrast(filament.getContrast());

                filamentBeans.add(filamentBean);
            }
        }

        return filamentBeans;
    }

    // query per REQ-FN-9

    public Map<String, Float> getStarsInsideFilamentByID(Integer filamentID) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        return dao.countStarsInsideFilamentByID(filamentID);

    }

    // query per REQ-FN-10 TODO

    // query per REQ-FN-11
    // GPoint getBranchMaxVertex(Branch branch) throws DaoException;
    // GPoint getBranchMinVertex(Branch branch) throws DaoException;
    // List<GPoint> getFilamentBoundary(Filament filament) throws DaoException

    // query per REQ-FN-12 TODO
}
