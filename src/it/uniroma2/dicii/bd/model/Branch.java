package it.uniroma2.dicii.bd.model;

import it.uniroma2.dicii.bd.enumeration.BranchType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Branch {

    private Integer idBranch;
    private BranchType type;
    private Filament filament;
    private List<GPointBranch> gPointBranch;

    public Branch(Integer idBranch, Filament filament) {

        gPointBranch = new ArrayList<GPointBranch>();

        this.idBranch = idBranch;
        this.filament = filament;
    }

    /**
     *
     * @param point add
     */
    public void addPoint(GPointBranch point){
        gPointBranch.add(point);
    }

    /**
     *
     * @return filament
     */
    public Filament getFilament() {
        return filament;
    }

    /**
     *
     * @return idBranch
     */
    public Integer getIdBranch() {
        return idBranch;
    }

    /**
     *
     * @return type
     * @see BranchType
     */
    public BranchType getType() {
        return type;
    }

    /**
     *
     * @param idBranch set
     */
    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    /**
     *
     * @param type set
     */
    public void setType(BranchType type) {
        this.type = type;
    }

    /**
     *
     * @param filament set
     */
    public void setFilament(Filament filament) {
        this.filament = filament;
    }

    /**
     *
     * @return list
     */
    public List<GPointBranch> getgPointBranch() {
        return gPointBranch;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "idBranch=" + idBranch +
                ", type=" + type +
                ", filament=" + filament +
                ", gPointBranch=" + gPointBranch +
                '}';
    }
}
