package it.uniroma2.dicii.bd.bean;


import it.uniroma2.dicii.bd.model.Tool;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class SatelliteBean {

    private String name;
    private Date firstObservation;
    private Date lastObservation;
    private String agency;
    private List<ToolBean> tools;

    public SatelliteBean(String name) {
        this.name = name;
    }

    /**
     *
     * @return tools
     */
    public List<ToolBean> getTools() {
        return tools;
    }

    /**
     *
     * @param tools set
     */
    public void setTools(List<ToolBean> tools) {
        this.tools = tools;
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
     * @return first observation
     */
    public Date getFirstObservation() {
        return firstObservation;
    }

    /**
     *
     * @return last observation
     */
    public Date getLastObservation() {
        return lastObservation;
    }

    /**
     *
     * @return date string formatted
     */
    public String getFirstObservationString() {

        SimpleDateFormat formatDateJava = new SimpleDateFormat("yyyy-MM-dd");
        return formatDateJava.format(this.getFirstObservation());
    }

    /**
     *
     * @return date string formatted
     */
    public String getLastObservationString() {

        SimpleDateFormat formatDateJava = new SimpleDateFormat("yyyy-MM-dd");
        return formatDateJava.format(this.getLastObservation());
    }

    /**
     *
     * @return agency name
     */
    public String getAgency() {
        return agency;
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
     * @param firstObservation set
     */
    public void setFirstObservation(Date firstObservation) {
        this.firstObservation = firstObservation;
    }

    /**
     *
     * @param lastObservation set
     */
    public void setLastObservation(Date lastObservation) {
        this.lastObservation = lastObservation;
    }

    /**
     *
     * @param agency set
     */
    public void setAgency(String agency) {
        this.agency = agency;
    }
}
