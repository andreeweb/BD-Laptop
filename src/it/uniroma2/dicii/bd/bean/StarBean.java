package it.uniroma2.dicii.bd.bean;

import it.uniroma2.dicii.bd.enumeration.StarType;

import java.math.BigDecimal;

public class StarBean {

    private Integer idStar;
    private String name;
    private BigDecimal flux;
    private StarType type;
    private GPointBean position;
    private ToolBean tool;

    private Double distanceFromFilamentSpine;
    private FilamentBean filament;

    public StarBean(Integer idStar) {
        this.idStar = idStar;
    }

    /**
     *
     * @return
     */
    public ToolBean getTool() {
        return tool;
    }

    /**
     *
     * @return
     */
    public GPointBean getPosition() {
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
    public void setPosition(GPointBean position) {
        this.position = position;
    }

    /**
     *
     * @param tool
     */
    public void setTool(ToolBean tool) {
        this.tool = tool;
    }

    /**
     *
     * @return
     */
    public Double getDistanceFromFilamentSpine() {
        return distanceFromFilamentSpine;
    }

    /**
     *
     * @param distanceFromFilamentSpine
     */
    public void setDistanceFromFilamentSpine(Double distanceFromFilamentSpine) {
        this.distanceFromFilamentSpine = distanceFromFilamentSpine;
    }

    /**
     *
     * @return
     */
    public FilamentBean getFilament() {
        return filament;
    }

    /**
     *
     * @param filament
     */
    public void setFilament(FilamentBean filament) {
        this.filament = filament;
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
