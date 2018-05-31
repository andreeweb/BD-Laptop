package it.uniroma2.dicii.bd.bean;

import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.model.Tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FilamentBean {

    private Integer idfil;
    private String name;
    private BigDecimal totalFlux;
    private BigDecimal meanDensity;
    private Float meanTemperature;
    private Float ellipticity;
    private Float contrast;
    private ToolBean tool;
    private List<GPointBean> boundary;
    private List<BranchBean> branches;

    public FilamentBean(Integer idfil) {

        this.idfil = idfil;

        this.branches = new ArrayList<>();
        this.boundary = new ArrayList<>();
    }

    /**
     *
     * @return branches
     */
    public List<BranchBean> getBranches() {
        return branches;
    }

    /**
     *
     * @param pointBean set
     */
    public void addBoundaryPoint(GPointBean pointBean){
        boundary.add(pointBean);
    }

    /**
     *
     * @param branchBean set
     */
    public void addBranch(BranchBean branchBean){
        branches.add(branchBean);
    }

    /**
     *
     * @return tool
     */
    public ToolBean getTool() {
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
    public List<GPointBean> getBoundary() {
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
     * @param toolBean set
     */
    public void setTool(ToolBean toolBean) {
        this.tool = toolBean;
    }
}
