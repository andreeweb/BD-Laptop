package it.uniroma2.dicii.bd.model;

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
    private List<GPoint> boundary;

    public Filament() {
        boundary = new ArrayList<>();
    }

    public void addBoundaryPoint(GPoint point){
        boundary.add(point);
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
                ", boundary=" + boundary +
                '}';
    }
}