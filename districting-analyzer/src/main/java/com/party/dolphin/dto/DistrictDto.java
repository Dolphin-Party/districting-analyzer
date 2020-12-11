package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.Set;

public class DistrictDto {
    /* Fields */
    private int districtId;
    private int districtingId;
    private Set<String> precincts;
    private int numberCounties;
    private DemographicType targetDemographic;
    private double targetDemographicPercentVAP;
    private int order;

    /* Properties */
    public int getDistrictId() {
        return this.districtId;
    }
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getDistrictingId() {
        return this.districtingId;
    }
    public void setDistrictingId(int districtingId) {
        this.districtingId = districtingId;
    }

    public Set<String> getPrecincts() {
        return this.precincts;
    }
    public void setPrecincts(Set<String> precincts) {
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