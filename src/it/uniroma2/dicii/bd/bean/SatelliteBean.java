package it.uniroma2.dicii.bd.bean;


import java.sql.Date;
import java.text.SimpleDateFormat;

public class SatelliteBean {

    private String name;
    private Date firstObservation;
    private Date lastObservation;
    private String agency;

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
