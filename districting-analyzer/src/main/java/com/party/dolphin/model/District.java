package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

import javax.persistence.*;

@Entity
public class District {
    /* Fields */
    private String districtId; // TODO: uuid, any uuid types?
    private List<Precinct> precincts;
    private int numberCounties;
    private DemographicType targetDemographic; // TODO: annotations for enum
    private double targetDemographicPercentVAP;
    private int order;

    /* Getters */
    @Id
    public String getDistrictId() {
        return this.districtId;
    }

    @ManyToMany
    public List<Precinct> getPrecincts() {
        return this.precincts;
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