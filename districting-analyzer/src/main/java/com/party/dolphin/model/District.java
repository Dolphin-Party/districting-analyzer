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

    /* Properties */
    @Id
    @GeneratedValue
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public Districting getDistricting() {
        return this.districting;
    }
    public void setDistricting(Districting districting) {
        this.districting = districting;
    }

    @ManyToMany
    public List<Precinct> getPrecincts() {
        return this.precincts;
    }
    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public int getNumberCounties() {
        return this.numberCounties;
    }
    public void setNumberCounties(int numberCounties) {
        this.numberCounties = numberCounties;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    public double getTargetDemographicPercentVAP() {
        return this.targetDemographicPercentVAP;
    }
    public void setTargetDemographicPercentVAP(double targetDemographicPercentVAP) {
        this.targetDemographicPercentVAP = targetDemographicPercentVAP;
    }

    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

}