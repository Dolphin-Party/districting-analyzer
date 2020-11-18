package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

import javax.persistence.*;

@Entity
public class Districting {
    /* Fields */
    private String districtingId; // TODO: uuid, any uuid types?
    private Job job;
    private DemographicType targetDemographic; // TODO: annotations for enum
    private int districtingIndex;
    private List<District> districts;

    /* Getters */
    @Id
    public String getDistrictingId() {
        return this.districtingId;
    }

    @ManyToOne
    public Job getJob() {
        return this.job;
    }

    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }

    public int getDistrictingIndex() {
        return this.districtingIndex;
    }

    @OneToMany
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