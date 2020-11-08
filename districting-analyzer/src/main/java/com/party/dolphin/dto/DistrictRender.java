package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

import javax.persistence.Id;

public class DistrictRender {
    /* Fields */
    private @Id String districtId;
    private List<Integer> precincts;
    private int numberCounties;
    private DemographicType targetDemographic; // TODO: String or enum?
    private double targetDemographicPercentVAP;
    private int order;

    /* Getters */
    public String getDistrictId() {
        return this.districtId;
    }

    public List<Integer> getPrecincts() {
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

}