package it.uniroma2.dicii.bd.model;

import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.BranchDao;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Filament {

    private Integer idfil;
    private String name;
    private BigDecimal totalFlux;
    private BigDecimal meanDensity;
    private Float meanTemperature;
    private Float ellipticity;
    private Float contrast;
    private Tool tool;
    private List<GPoint> boundary;
    private List<Branch> branches;

    public Filament(Integer idfil) {

        this.idfil = idfil;

        this.branches = new ArrayList<>();
        this.boundary = new ArrayList<>();
    }

    /**
     *
     * @param boundary set
     */
    public void setBoundary(List<GPoint> boundary) {
        this.boundary = boundary;
    }

    /**
     *
     * @param branches set
     */
    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    /**
     *
     * @return branches
     */
    public List<Branch> getBranches() {
        return branches;
    }

    /**
     *
     * @param point set
     */
    public void addBoundaryPoint(GPoint point){
        boundary.add(point);
    }

    /**
     *
     * @param branch set
     */
    public void addBranch(Branch branch){
        branches.add(branch);
    }

    /**
     *
     * @return tool
     */
    public Tool getTool() {
        return tool;
    }

    /**
     *
     * @return idfil
     */
    public Integer getIdfil() {
        return idfil;
    }

    /**
     *
     * @return boundary
     */
    public List<GPoint> getBoundary() {
        return boundary;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return totalFlux
     */
    public BigDecimal getTotalFlux() {
        return totalFlux;
    }

    /**
     *
     * @return meanDensity
     */
    public BigDecimal getMeanDensity() {
        return meanDensity;
    }

    /**
     *
     * @return meanTemperature
     */
    public Float getMeanTemperature() {
        return meanTemperature;
    }

    /**
     *
     * @return ellipticity
     */
    public Float getEllipticity() {
        return ellipticity;
    }

    /**
     *
     * @return contrast
     */
    public Float getContrast() {
        return contrast;
    }

    /**
     *
     * @param idfil set
     */
    public void setIdfil(Integer idfil) {
        this.idfil = idfil;
    }

    /**
     *
     * @param name set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param totalFlux set
     */
    public void setTotalFlux(BigDecimal totalFlux) {
        this.totalFlux = totalFlux;
    }

    /**
     *
     * @param meanDensity set
     */
    public void setMeanDensity(BigDecimal meanDensity) {
        this.meanDensity = meanDensity;
    }

    /**
     *
     * @param meanTemperature set
     */
    public void setMeanTemperature(float meanTemperature) {
        this.meanTemperature = meanTemperature;
    }

    /**
     *
     * @param ellipticity set
     */
    public void setEllipticity(float ellipticity) {
        this.ellipticity = ellipticity;
    }

    /**
     *
     * @param contrast set
     */
    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    /**
     *
     * @param tool set
     */
    public void setTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public String toString() {
        return "Filament{" +
                "idfil=" + idfil +
                ", name='" + name + '\'' +
                ", totalFlux=" + totalFlux +
                ", meanDensity=" + meanDensity +
                ", meanTemperature=" + meanTemperature +
                ", ellipticity=" + ellipticity +
                ", contrast=" + contrast +
                ", tool=" + tool +
                ", boundary=" + boundary +
                ", branches=" + branches +
                '}';
    }

    /**
     * test
     * @param args
     */
    public static void main(String[] args) throws DaoException {

        FilamentDao filamentDao = DaoFactory.getSingletonInstance().getFilamentDAO();

        /*GPoint point = filamentDao.getFilamentCentroidByID(id);
        System.out.println("Centroid by ID: " + point.getGlatitude() + " " + point.getGlongitude());

        point = filamentDao.getFilamentCentroidByName("HiGALFil013.8059-1.2194");
        System.out.println("Centroid by Name: " + point.getGlatitude() + " " + point.getGlongitude());

        point = filamentDao.getFilamentExtensionByID(id);
        System.out.println("Extension by ID: " + point.getGlatitude() + " " + point.getGlongitude());

        point = filamentDao.getFilamentExtensionByName("HiGALFil013.8059-1.2194");
        System.out.println("Extension by Name: " + point.getGlatitude() + " " + point.getGlongitude());*/

        Integer numberOfsegment = filamentDao.getCountFilamentSegmentByID(2885);
        System.out.println("Number of segment by ID: " + numberOfsegment);

        /*numberOfsegment = filamentDao.getCountFilamentSegmentByName("HiGALFil013.8059-1.2194");
        System.out.println("Number of segment by Name: " + numberOfsegment);*/

        /*List<Filament> filamentList = filamentDao.getFilamentsByLuminanceAndEllipticity(10.0, 2.0f, 9.0f);
        for (Filament filament : filamentList){
            System.out.println(filament.getName() + " " + filament.getEllipticity() + " " + filament.getContrast());
        }*/

        List<Filament> filamentList = filamentDao.getFilamentsByNumberOfSegments(19, 19);
        for (Filament filament : filamentList){
            System.out.println(filament.getIdfil());
        }

        /*Branch branch = new Branch(31, new Filament(52));

        BranchDao branchDao = DaoFactory.getSingletonInstance().getBranchDAO();

        GPoint point = branchDao.getBranchMaxVertex(branch);
        System.out.println("Max vertex: " + point.getGlongitude() + " " + point.getGlatitude());
        point = branchDao.getBranchMinVertex(branch);
        System.out.println("Min vertex: " + point.getGlongitude() + " " + point.getGlatitude());*/

        /*List<GPoint> boundary = filamentDao.getFilamentBoundary(branch.getFilament());
        for (GPoint p : boundary){
            System.out.println(p.getGlatitude() + " " + p.getGlongitude());
        }*/
    }
}
