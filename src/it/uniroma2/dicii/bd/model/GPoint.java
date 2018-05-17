package it.uniroma2.dicii.bd.model;

public class GPoint {

    private float glongitude;
    private float glatitude;

    public GPoint(float glongitude, float glatitude) {
        this.glongitude = glongitude;
        this.glatitude = glatitude;
    }

    /**
     *
     * @return Galactic longitude
     */
    public float getGlongitude() {
        return glongitude;
    }

    /**
     *
     * @return Galactic latitude
     */
    public float getGlatitude() {
        return glatitude;
    }

    /**
     *
     * @param glongitude set
     */
    public void setGlongitude(float glongitude) {
        this.glongitude = glongitude;
    }

    /**
     *
     * @param glatitude set
     */
    public void setGlatitude(float glatitude) {
        this.glatitude = glatitude;
    }
}
