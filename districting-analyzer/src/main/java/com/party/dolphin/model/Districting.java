package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.List;

import javax.persistence.Id;

public class Districting {
    /* Fields */
    private @Id String districtingId;
    private Job job;
    private DemographicType targetDemographic;
    private int districtingIndex;
    private List<District> districts;

    /* Getters */
    public String getDistrictingId() {
        return this.districtingId;
    }

    public Job getJob() {
        return this.job;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }

    public int getDistrictingIndex() {
        return this.districtingIndex;
    }

    public List<District> getDistricts() {
        return this.districts;
    }

    /* Setters */
    // TODO: setTargetDemographic needed?
	public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }
    public void setDistrictingIndex(int districtingIndex) {
        this.districtingIndex = districtingIndex;
    }
    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }


}