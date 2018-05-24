package it.uniroma2.dicii.bd.model;

import java.sql.Date;

public class Satellite {

    private String name;
    private Date firstObservation;
    private Date lastObservation;
    private String agency;

    public Satellite(String name) {
        this.name = name;
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
