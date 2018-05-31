package it.uniroma2.dicii.bd.model;

public class Tool {

    private String name;
    private String band;
    private Satellite satellite;

    public Tool(String name) {
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
    public Satellite getSatellite() {
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
    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }
}
