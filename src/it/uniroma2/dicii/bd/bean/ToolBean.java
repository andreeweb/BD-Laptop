package it.uniroma2.dicii.bd.bean;

public class ToolBean {

    private String name;
    private String band;
    private SatelliteBean satellite;

    public ToolBean(String name) {
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
     * @return band
     */
    public String getBand() {
        return band;
    }

    /**
     *
     * @return satellite
     */
    public SatelliteBean getSatellite() {
        return satellite;
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
     * @param band set
     */
    public void setBand(String band) {
        this.band = band;
    }

    /**
     *
     * @param satellite set
     */
    public void setSatellite(SatelliteBean satellite) {
        this.satellite = satellite;
    }
}
