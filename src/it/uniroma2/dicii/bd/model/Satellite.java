package it.uniroma2.dicii.bd.model;

import it.uniroma2.dicii.bd.enumeration.StarType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Satellite {

    private String name;
    private Date firstObservation;
    private Date lastObservation;
    private String agency;
    private List<Tool> tools;

    public Satellite(String name) {
        this.name = name;
        tools = new ArrayList<>();
    }

    public Tool getToolForTypeStar(StarType type) throws Exception {

        // stella lambda = 8μm
        // prestella lambda > 160μm
        // prostella lambda = 70μm

        switch (type){
            case UNBOUND:{

                return new Tool(null);
            }
            case STAR:{

                for (Tool tool : this.tools){
                    List<Float> bands = tool.getBands();

                    for (Float band : bands){
                        System.out.println(band);
                    }

                    if (bands.contains(8.0f)){
                        return tool;
                    }
                }

                break;
            }
            case PRESTELLAR:{

                for (Tool tool : this.tools){
                    List<Float> bands = tool.getBands();
                    for (Float band : bands){
                        if (band >= 160.0f){
                            return tool;
                        }
                    }

                }

                break;
            }
            case PROTOSTELLAR:{

                for (Tool tool : this.tools){
                    List<Float> bands = tool.getBands();
                    if (bands.contains(70.0f)){
                        return tool;
                    }
                }

                break;
            }
            default:{

                throw new Exception("No tool found");
            }
        }

        return null;
    }

    /**
     *
     * @return tools
     */
    public List<Tool> getTools() {
        return tools;
    }

    /**
     *
     * @param tools set
     */
    public void setTools(List<Tool> tools) {
        this.tools = tools;
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
     * @return first observation
     */
    public Date getFirstObservation() {
        return firstObservation;
    }

    /**
     *
     * @return last observation
     */
    public Date getLastObservation() {
        return lastObservation;
    }

    /**
     *
     * @return agency name
     */
    public String getAgency() {
        return agency;
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
     * @param firstObservation set
     */
    public void setFirstObservation(Date firstObservation) {
        this.firstObservation = firstObservation;
    }

    /**
     *
     * @param lastObservation set
     */
    public void setLastObservation(Date lastObservation) {
        this.lastObservation = lastObservation;
    }

    /**
     *
     * @param agency set
     */
    public void setAgency(String agency) {
        this.agency = agency;
    }
}
