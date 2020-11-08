package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.List;

import javax.persistence.Id;

public class Job {
    /* Fields */
    private @Id String jobId;
    private JobStatus status;
    private State state;
    private int numberDistrictings;
    private String compactnessAmoung; // TODO
    private boolean isSeawulf;
    private List<Districting> districtings;
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

    public State getState() {
        return this.state;
    }

    public int getNumberDistrictings() {
        return this.numberDistrictings;
    }

    public String getCompactnessAmoung() {
        return this.compactnessAmoung;
    }

    public boolean getIsSeawulf() {
        return this.isSeawulf;
    }

    public boolean isIsSeawulf() {
        return this.isSeawulf;
    }

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
	public void setJobId(String jobId) {
        this.jobId = jobId;
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
    public void setCompactnessAmoung(String compactnessAmoung) {
        this.compactnessAmoung = compactnessAmoung;
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