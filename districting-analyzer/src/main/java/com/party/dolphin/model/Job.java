package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.*;

import java.util.List;

import javax.persistence.*;

@Entity
public class Job {
    /* Fields */
    private int id;
    private JobStatus status; // TODO: annotations for enum
    private State state;
    private int numberDistrictings;
    private String compactnessAmount; // TODO: enum, int, or String?
    private DemographicType targetDemographic; // TODO: annotations for enum
    private boolean isSeawulf;
    private List<Districting> districtings;
    private List<BoxWhisker> boxWhiskerData;
    private int averageDistricting;
    private int extremeDistricting;
    
    /* Properties */
    @Id
    @GeneratedValue
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return this.status;
    }
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @ManyToOne
    public State getState() {
        return this.state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public int getNumberDistrictings() {
        return this.numberDistrictings;
    }
    public void setNumberDistrictings(int numberDistrictings) {
        this.numberDistrictings = numberDistrictings;
    }

    public String getCompactnessAmount() {
        return this.compactnessAmount;
    }
    public void setCompactnessAmount(String compactnessAmount) {
        this.compactnessAmount = compactnessAmount;
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

    @OneToMany
    public List<Districting> getDistrictings() {
        return this.districtings;
    }
    public void setDistrictings(List<Districting> districtings) {
        this.districtings = districtings;
    }

    @OneToMany(mappedBy="job")
    public List<BoxWhisker> getBoxWhiskerData() {
        return this.boxWhiskerData;
    }
    public void setBoxWhiskerData(List<BoxWhisker> boxWhiskerData) {
        this.boxWhiskerData = boxWhiskerData;
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