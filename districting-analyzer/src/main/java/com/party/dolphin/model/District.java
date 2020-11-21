package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

import javax.persistence.*;

@Entity
public class District {
    /* Fields */
    private int id;
    private Districting districting;
    private List<Precinct> precincts;
    private int numberCounties;
    private DemographicType targetDemographic; // TODO: annotations for enum
    private double targetDemographicPercentVAP;
    private int order;

    /* Getters */
    @Id
    @GeneratedValue
    public int getId() {
        return this.id;
    }

    @ManyToMany
    public List<Precinct> getPrecincts() {
        return this.precincts;
    }

    @ManyToOne
    public Districting getDistricting() {
        return this.districting;
    }

    public int getNumberCounties() {
        return this.numberCounties;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }

    public double getTargetDemographicPercentVAP() {
        return this.targetDemographicPercentVAP;
    }

    public int getOrder() {
        return this.order;
    }

    /* Setters*/
    // TODO: setPrecincts needed?
	public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }
    public void setNumberCounties(int numberCounties) {
        this.numberCounties = numberCounties;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }
    public void setTargetDemographicPercentVAP(double targetDemographicPercentVAP) {
        this.targetDemographicPercentVAP = targetDemographicPercentVAP;
    }
    public void setOrder(int order) {
        this.order = order;
    }

}