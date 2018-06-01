package it.uniroma2.dicii.bd.model;

public class GPoint {

    private Double glongitude;
    private Double glatitude;

    public GPoint(Double glongitude, Double glatitude) {
        this.glongitude = glongitude;
        this.glatitude = glatitude;
    }

    /**
     *
     * @return Galactic longitude
     */
    public Double getGlongitude() {
        return glongitude;
    }

    /**
     *
     * @return Galactic latitude
     */
    public Double getGlatitude() {
        return glatitude;
    }

    /**
     *
     * @param glongitude set
     */
    public void setGlongitude(Double glongitude) {
        this.glongitude = glongitude;
    }

    /**
     *
     * @param glatitude set
     */
    public void setGlatitude(Double glatitude) {
        this.glatitude = glatitude;
    }

    @Override
    public String toString() {
        return "GPoint{" +
                "glongitude=" + glongitude +
                ", glatitude=" + glatitude +
                '}';
    }
}
