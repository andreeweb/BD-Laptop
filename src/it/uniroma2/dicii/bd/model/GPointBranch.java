package it.uniroma2.dicii.bd.model;

import java.math.BigDecimal;

public class GPointBranch extends GPoint{

    private Integer sequenceNumber;
    private BigDecimal flux;

    public GPointBranch(double glongitude, double glatitude, Integer sequenceNumber) {
        super(glongitude, glatitude);
        this.sequenceNumber = sequenceNumber;
    }

    /**
     *
     * @param flux set
     */
    public void setFlux(BigDecimal flux) {
        this.flux = flux;
    }

    /**
     *
     * @param sequenceNumber set
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     *
     * @return flux
     */
    public BigDecimal getFlux() {
        return flux;
    }

    /**
     *
     * @return sequence number
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

}
