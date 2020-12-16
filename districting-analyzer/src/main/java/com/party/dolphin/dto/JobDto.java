package com.party.dolphin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.party.dolphin.model.enums.*;

import java.util.List;

public class JobDto {
    /* Fields */
    private int jobId;
    private JobStatus status;
    private int stateId;
    private int numberDistrictings;
    private int iterations;
    private double compactnessAmount; // TODO: See Job.java
    private double percentDiff;
    private DemographicType targetDemographic; // TODO: enum or String?
    private boolean isSeawulf;
    private List<Integer> districtings;
    private List<BoxWhiskerDto> boxWhiskers;
    private int averageDistricting;
    private int extremeDistricting;

    /* Properties */
    public int getJobId() {
        return this.jobId;
    }
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public JobStatus getStatus() {
        return this.status;
    }
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public int getStateId() {
        return this.stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getNumberDistrictings() {
        return this.numberDistrictings;
    }
    public void setNumberDistrictings(int numberDistrictings) {
        this.numberDistrictings = numberDistrictings;
    }

    @JsonProperty(access=Access.WRITE_ONLY)
    public int getIterations() {
        return this.iterations;
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getCompactnessAmount() {
        return this.compactnessAmount;
    }
    public void setCompactnessAmount(double compactnessAmount) {
        this.compactnessAmount = compactnessAmount;
    }

    public double getPercentDiff() {
        return this.percentDiff;
    }
    public void setPercentDiff(double percentDiff) {
        this.percentDiff = percentDiff;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    public boolean getIsSeawulf() {
        return this.isSeawulf;
    }
    public void setIsSeawulf(boolean isSeawulf) {
        this.isSeawulf = isSeawulf;
    }

    public List<Integer> getDistrictings() {
        return this.districtings;
    }
    public void setDistrictings(List<Integer> districtings) {
        this.districtings = districtings;
    }

    public List<BoxWhiskerDto> getBoxWhiskers() {
        return this.boxWhiskers;
    }
    public void setBoxWhiskers(List<BoxWhiskerDto> boxWhiskerData) {
        this.boxWhiskers = boxWhiskerData;
    }

    public int getAverageDistricting() {
        return this.averageDistricting;
    }
    public void setAverageDistricting(int averageDistricting) {
        this.averageDistricting = averageDistricting;
    }

    public int getExtremeDistricting() {
        return this.extremeDistricting;
    }
    public void setExtremeDistricting(int extremeDistricting) {
        this.extremeDistricting = extremeDistricting;
    }

}