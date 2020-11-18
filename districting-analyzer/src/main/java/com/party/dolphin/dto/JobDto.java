package com.party.dolphin.dto;

import com.party.dolphin.model.enums.*;

import java.util.List;

public class JobDto {
    /* Fields */
    private String jobId;
    private JobStatus status;
    private int stateId;
    private int numberDistrictings;
    private String compactnessAmount; // TODO: See Job.java
    private DemographicType targetDemographic; // TODO: enum or String?
    private boolean isSeawulf;
    private List<String> districtings;
    private List<List<Double>> boxWhiskerData;
    private int averageDistricting;
    private int extremeDistricting;

    /* Getters */
    public String getJobId() {
        return this.jobId;
    }

    public JobStatus getStatus() {
        return this.status;
    }

    public int getStateId() {
        return this.stateId;
    }

    public int getNumberDistrictings() {
        return this.numberDistrictings;
    }

    public String getCompactnessAmount() {
        return this.compactnessAmount;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }

    public boolean getIsSeawulf() {
        return this.isSeawulf;
    }

    public boolean isIsSeawulf() {
        return this.isSeawulf;
    }

    public List<String> getDistrictings() {
        return this.districtings;
    }

    public List<List<Double>> getBoxWhiskerData() {
        return this.boxWhiskerData;
    }

    public int getAverageDistricting() {
        return this.averageDistricting;
    }

    public int getExtremeDistricting() {
        return this.extremeDistricting;
    }

}