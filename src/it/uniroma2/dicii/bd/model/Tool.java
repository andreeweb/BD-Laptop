package it.uniroma2.dicii.bd.model;

import java.util.ArrayList;
import java.util.List;

public class Tool {

    private String name;
    private Satellite satellite;
    private String band;
    private List<Float> bands;

    public Tool(String name) {
        this.name = name;
        this.bands = new ArrayList<>();
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
    public String getBandString() {
        return band;
    }

    /**
     *
     * @return band
     */
    public List<Float> getBands() {
        return bands;
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

        for (String val: band.split(",")) {
            bands.add(Float.valueOf(val));
        }
    }

    /**
     *
     * @param satellite set
     */
    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "name='" + name + '\'' +
                ", satellite=" + satellite +
                ", band='" + band + '\'' +
                ", bands=" + bands +
                '}';
    }
}

