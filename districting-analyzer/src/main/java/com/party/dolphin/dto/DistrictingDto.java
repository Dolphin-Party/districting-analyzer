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

    /* Getters */
    public int getDistrictingId() {
        return this.districtingId;
    }

    public int getJobId() {
        return this.jobId;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }

    public int getDistrictingIndex() {
        return this.districtingIndex;
    }

    public List<Integer> getDistricts() {
        return this.districts;
    }
    
}