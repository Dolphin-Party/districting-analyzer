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
    private List<List<Double>> boxWhiskerData;
    private int averageDistricting;
    private int extremeDistricting;
    
    /* Getters */
    @Id
    @GeneratedValue
    public int getId() {
        return this.id;
    }

    public JobStatus getStatus() {
        return this.status;
    }

    @ManyToOne
    public State getState() {
        return this.state;
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

    @OneToMany
    public List<Districting> getDistrictings() {
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

    /* Setters */
	public void setJobId(int id) {
        this.id = id;
    }
    public void setStatus(JobStatus status) {
        this.status = status;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setNumberDistrictings(int numberDistrictings) {
        this.numberDistrictings = numberDistrictings;
    }
    public void setCompactnessAmount(String compactnessAmount) {
        this.compactnessAmount = compactnessAmount;
    }
    public void setIsSeawulf(boolean isSeawulf) {
        this.isSeawulf = isSeawulf;
    }
    public void setDistrictings(List<Districting> districtings) {
        this.districtings = districtings;
    }
    public void setBoxWhiskerData(List<List<Double>> boxWhiskerData) {
        this.boxWhiskerData = boxWhiskerData;
    }
    public void setAverageDistricting(int averageDistricting) {
        this.averageDistricting = averageDistricting;
    }
    public void setExtremeDistricting(int extremeDistricting) {
        this.extremeDistricting = extremeDistricting;
    }    

}