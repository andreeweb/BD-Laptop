package it.uniroma2.dicii.bd.model;

import it.uniroma2.dicii.bd.enumeration.StarType;

import java.math.BigDecimal;

public class Star {

    private Integer idStar;
    private String name;
    private BigDecimal flux;
    private StarType type;
    private GPoint position;
    private Tool tool;

    public Star(Integer idStar) {
        this.idStar = idStar;
    }

    /**
     *
     * @return
     */
    public Tool getTool() {
        return tool;
    }

    /**
     *
     * @return
     */
    public GPoint getPosition() {
        return position;
    }

    /**
     *
     * @param idStar
     */
    public void setIdStar(Integer idStar) {
        this.idStar = idStar;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param flux
     */
    public void setFlux(BigDecimal flux) {
        this.flux = flux;
    }

    /**
     *
     * @param type
     */
    public void setType(StarType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Integer getIdStar() {
        return idStar;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public BigDecimal getFlux() {
        return flux;
    }

    /**
     *
     * @return
     */
    public StarType getType() {
        return type;
    }

    /**
     *
     * @param position
     */
    public void setPosition(GPoint position) {
        this.position = position;
    }

    /**
     *
     * @param tool
     */
    public void setTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public String toString() {
        return "Star{" +
                "idStar=" + idStar +
                ", name='" + name + '\'' +
                ", flux=" + flux +
                ", type=" + type +
                ", position=" + position +
                ", tool=" + tool.getName() +
                '}';
    }
}
