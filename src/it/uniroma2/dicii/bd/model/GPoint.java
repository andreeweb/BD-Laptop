package it.uniroma2.dicii.bd.model;

public class GPoint {

    private double glongitude;
    private double glatitude;

    public GPoint(double glongitude, double glatitude) {
        this.glongitude = glongitude;
        this.glatitude = glatitude;
    }

    /**
     *
     * @return Galactic longitude
     */
    public double getGlongitude() {
        return glongitude;
    }

    /**
     *
     * @return Galactic latitude
     */
    public double getGlatitude() {
        return glatitude;
    }

    /**
     *
     * @param glongitude set
     */
    public void setGlongitude(double glongitude) {
        this.glongitude = glongitude;
    }

    /**
     *
     * @param glatitude set
     */
    public void setGlatitude(double glatitude) {
        this.glatitude = glatitude;
    }
}
