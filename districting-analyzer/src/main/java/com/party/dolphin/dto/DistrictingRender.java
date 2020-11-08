package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

import javax.persistence.Id;

public class DistrictingRender {
    /* Fields */
    private @Id String districtingId;
    private String jobId;
    private DemographicType targetDemographic;
    private int districtingIndex;
    private List<String> districts;

    /* Getters */
    public String getDistrictingId() {
        return this.districtingId;
    }

    public String getJobId() {
        return this.jobId;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }

    public int getDistrictingIndex() {
        return this.districtingIndex;
    }

    public List<String> getDistricts() {
        return this.districts;
    }
    
}