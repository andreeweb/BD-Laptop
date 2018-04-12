package it.uniroma2.dicii.bd.model;

import java.math.BigDecimal;

public class Filament {

    private Integer idfil;
    private String name;
    private BigDecimal totalFlux;
    private BigDecimal meanDensity;
    private Float meanTemperature;
    private Float ellipticity;
    private Float contrast;

    public Integer getIdfil() {
        return idfil;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTotalFlux() {
        return totalFlux;
    }

    public BigDecimal getMeanDensity() {
        return meanDensity;
    }

    public Float getMeanTemperature() {
        return meanTemperature;
    }

    public Float getEllipticity() {
        return ellipticity;
    }

    public Float getContrast() {
        return contrast;
    }

    public void setIdfil(Integer idfil) {
        this.idfil = idfil;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalFlux(BigDecimal totalFlux) {
        this.totalFlux = totalFlux;
    }

    public void setMeanDensity(BigDecimal meanDensity) {
        this.meanDensity = meanDensity;
    }

    public void setMeanTemperature(float meanTemperature) {
        this.meanTemperature = meanTemperature;
    }

    public void setEllipticity(float ellipticity) {
        this.ellipticity = ellipticity;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }
}
