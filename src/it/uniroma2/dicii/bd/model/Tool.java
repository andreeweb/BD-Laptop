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
     * Check if tool have this band
     *
     * @param band
     * @return
     */
    public Boolean haveBandEquals(Float band){

        if (this.bands.contains(band))
            return true;

        return false;
    }

    /**
     *
     * Check if tool have this band
     *
     * @param band
     * @return
     */
    public Boolean haveBandGreaterThan(Float band){

        for (Float b : this.bands){
            if (b > band){
                return true;
            }
        }

        return false;
    }

    /**
     *
     * Check if tool have this band
     *
     * @param band
     * @return
     */
    public Boolean haveBandGreaterEqualThan(Float band){

        return this.haveBandEquals(band) && this.haveBandGreaterThan(band);
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

