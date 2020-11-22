package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.DemographicType;

import java.util.List;

import javax.persistence.*;

@Entity
public class Districting {
    /* Fields */
    private int id;
    private Job job;
    private DemographicType targetDemographic; // TODO: annotations for enum
    private int districtingIndex;
    private List<District> districts;

    /* Properties */
    @Id
    @GeneratedValue
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public Job getJob() {
        return this.job;
    }
    public void setJob(Job job) {
        this.job = job;
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

    @OneToMany(mappedBy="districting")
    public List<District> getDistricts() {
        return this.districts;
    }
    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

}