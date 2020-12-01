package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

public class DistrictingDto {
    /* Fields */
    private int districtingId;
    private int jobId;
    private DemographicType targetDemographic;
    private int districtingIndex;
    private List<Integer> districts;

    /* Properties */
    public int getDistrictingId() {
        return this.districtingId;
    }
    public void setDistrictingId(int districtingId) {
        this.districtingId = districtingId;
    }

    public int getJobId() {
        return this.jobId;
    }
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    public int getDistrictingIndex() {
        return this.districtingIndex;
    }
    public void setDistrictingIndex(int districtingIndex) {
        this.districtingIndex = districtingIndex;
    }

    public List<Integer> getDistricts() {
        return this.districts;
    }
    public void setDistricts(List<Integer> districts) {
        this.districts = districts;
    }

    
}