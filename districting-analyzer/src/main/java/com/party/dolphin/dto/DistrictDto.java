package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

public class DistrictDto {
    /* Fields */
    private int districtId;
    private int districtingId;
    private List<Integer> precincts;
    private int numberCounties;
    private DemographicType targetDemographic; // TODO: String or enum?
    private double targetDemographicPercentVAP;
    private int order;

    /* Getters */
    public int getDistrictId() {
        return this.districtId;
    }

    public int getDistrictingId() {
        return this.districtingId;
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