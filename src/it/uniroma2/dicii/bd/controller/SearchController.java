package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.FilamentBean;
import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.bean.StarBean;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.enumeration.StarType;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.BranchDao;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.interfaces.StarDao;
import it.uniroma2.dicii.bd.model.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     *
     * @return
     * @throws DaoException
     */
    public Integer getCountFilamentInDB() throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
        return dao.getCountFilament();
    }

    /**
     *
     * @return
     * @throws DaoException
     */
    public Integer getCountFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max) throws DaoException {

        FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
        return dao.getCountFilamentsByLuminanceAndEllipticity(percentageLuminance, ellipticity_min, ellipticity_max);
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

    /**
     *
     * @param filamentID
     * @return
     * @throws DaoException
     */
    public Map<String, Float> countStarsInsideFilamentByID(Integer filamentID) throws DaoException {

        StarDao dao = DaoFactory.getSingletonInstance().getStarDAO();
        List<Star> starList = dao.getStarsInsideFilamentByID(filamentID);

        // for calculating
        Float numberOfProtostellar = 0.0f;
        Float numberOfPrestellar = 0.0f;
        Float numberOfUnbound = 0.0f;
        Float totalStar = 0.0f;

        for (Star star : starList) {

            switch (star.getType()) {

                case PROTOSTELLAR: {
                    numberOfProtostellar++;
                    totalStar++;
                    break;
                }

                case PRESTELLAR: {
                    numberOfPrestellar++;
                    totalStar++;
                    break;
                }

                case UNBOUND: {
                    numberOfUnbound++;
                    totalStar++;
                    break;
                }
            }
        }

        Map<String, Float> map = new HashMap<>();
        map.put("totalStar", totalStar);
        map.put("percentageOfProtostellar", (numberOfProtostellar / totalStar) * 100);
        map.put("percentageOfPrestellar", (numberOfPrestellar / totalStar) * 100);
        map.put("percentageOfUnbound", (numberOfUnbound / totalStar) * 100);

        return map;
    }

    // query per REQ-FN-10

    public Map<String, Float> getStarInsideRectRegion(GPointBean center, Float side1, Float side2) throws DaoException {

        GPoint gPointCenter = new GPoint(center.getGlongitude(), center.getGlatitude());

        StarDao dao = DaoFactory.getSingletonInstance().getStarDAO();
        List<Star> starList = dao.getStarsInsideRectRegion(gPointCenter, side1, side2);

        // for calculating
        Float numberOfProtostellarInsideFilament = 0.0f;
        Float numberOfPrestellarInsideFilament = 0.0f;
        Float numberOfUnboundInsideFilament = 0.0f;
        Float totalStarInsideFilament = 0.0f;

        Float numberOfProtostellarOutsideFilament = 0.0f;
        Float numberOfPrestellarOutsideFilament = 0.0f;
        Float numberOfUnboundOutsideFilament = 0.0f;
        Float totalStarOutsideFilament = 0.0f;

        Float totalStar = 0.0f;

        List<Star> starWithfilament = dao.areInsideFilament(starList);

        for (Star star : starWithfilament) {

            switch (star.getType()) {

                case PROTOSTELLAR: {

                    if (star.getFilament() != null){
                        numberOfProtostellarInsideFilament++;
                        totalStarInsideFilament++;
                    }else{
                        numberOfProtostellarOutsideFilament++;
                        totalStarOutsideFilament++;
                    }

                    totalStar++;
                    break;
                }

                case PRESTELLAR: {

                    if (star.getFilament() != null){
                        numberOfPrestellarInsideFilament++;
                        totalStarInsideFilament++;
                    }else{
                        numberOfPrestellarOutsideFilament++;
                        totalStarOutsideFilament++;
                    }

                    totalStar++;
                    break;
                }

                case UNBOUND: {

                    if (star.getFilament() != null){
                        numberOfUnboundInsideFilament++;
                        totalStarInsideFilament++;
                    }else{
                        numberOfUnboundOutsideFilament++;
                        totalStarOutsideFilament++;
                    }

                    totalStar++;
                    break;
                }
            }
        }

        Map<String, Float> map = new HashMap<>();

        map.put("totalStar", totalStar);
        map.put("totalStarInsideFilament", totalStarInsideFilament);
        map.put("totalStarOutsideFilament", totalStarOutsideFilament);

        map.put("percentageOfProtostellarInsideFilament", (numberOfProtostellarInsideFilament / totalStarInsideFilament) * 100);
        map.put("percentageOfPrestellarInsideFilament", (numberOfPrestellarInsideFilament / totalStarInsideFilament) * 100);
        map.put("percentageOfUnboundInsideFilament", (numberOfUnboundInsideFilament / totalStarInsideFilament) * 100);

        map.put("percentageOfProtostellarOutsideFilament", (numberOfProtostellarOutsideFilament / totalStarOutsideFilament) * 100);
        map.put("percentageOfPrestellarOutsideFilament", (numberOfPrestellarOutsideFilament / totalStarOutsideFilament) * 100);
        map.put("percentageOfUnboundOutsideFilament", (numberOfUnboundOutsideFilament / totalStarOutsideFilament) * 100);

        return map;
    }

    // query per REQ-FN-11

    /**
     *
     * 0 max vertex, 1 min vertex
     *
     * @param branchID
     * @param filamentID
     * @return
     * @throws DaoException
     */
    public List<GPointBean> getBranchVertex(Integer branchID, Integer filamentID) throws DaoException {

        List<GPointBean> vertex = new ArrayList<>();
        BranchDao branchDao = DaoFactory.getSingletonInstance().getBranchDAO();

        Branch branch = new Branch(branchID, new Filament(filamentID));

        GPoint maxVertex = branchDao.getBranchMaxVertex(branch);
        GPoint minVertex = branchDao.getBranchMinVertex(branch);

        GPointBean maxBeanVertex = new GPointBean(maxVertex.getGlongitude(), maxVertex.getGlatitude());
        GPointBean minBeanVertex = new GPointBean(minVertex.getGlongitude(), minVertex.getGlatitude());

        vertex.add(maxBeanVertex);
        vertex.add(minBeanVertex);

        return vertex;
    }

    /**
     *
     * @param vertex
     * @param filamentID
     * @return
     * @throws DaoException
     */
    public Double getVertexDistanceFromBoundary(GPointBean vertex, Integer filamentID) throws DaoException {

        FilamentDao filamentDao = DaoFactory.getSingletonInstance().getFilamentDAO();

        List<GPoint> boundary = filamentDao.getFilamentBoundary(new Filament(filamentID));

        Double minDistance = Double.MAX_VALUE;

        for (GPoint p : boundary){

            Double distance = Math.sqrt(
                                Math.pow((vertex.getGlongitude()-p.getGlongitude()),2) +
                                Math.pow((vertex.getGlatitude()-p.getGlatitude()),2));

            if (distance < minDistance)
                minDistance = distance;
        }

        return minDistance;
    }

    // query per REQ-FN-12

    /**
     *
     * @param filamentID
     * @return
     * @throws DaoException
     */
    public List<StarBean> getStarsInsideFilament(Integer filamentID) throws DaoException {

        StarDao starDAO = DaoFactory.getSingletonInstance().getStarDAO();
        List<Star> starList = starDAO.getStarsInsideFilamentByID(filamentID);

        FilamentDao filamentDao = DaoFactory.getSingletonInstance().getFilamentDAO();
        Branch spine = filamentDao.getFilamentSpine(new Filament(filamentID));

        List<StarBean> starBeansList = new ArrayList<>();

        for (Star star : starList) {

            StarBean starBean = new StarBean(star.getIdStar());
            starBean.setName(star.getName());
            starBean.setFlux(star.getFlux());
            starBean.setDistanceFromFilamentSpine(Double.MAX_VALUE);

            for (GPointBranch gPointBranch : spine.getgPointBranch()) {

                Double distance = Math.sqrt(
                        Math.pow((star.getPosition().getGlongitude() - gPointBranch.getGlongitude()), 2) +
                                Math.pow((star.getPosition().getGlatitude() - gPointBranch.getGlatitude()), 2));

                if (distance < starBean.getDistanceFromFilamentSpine())
                    starBean.setDistanceFromFilamentSpine(distance);
            }

            starBeansList.add(starBean);
        }

        return starBeansList;
    }
}
