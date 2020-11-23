package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.*;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Jobs")
public class Job {
    /* Fields */
    private int id;
    private JobStatus status;
    private State state;
    private int numberDistrictings;
    private String compactnessAmount; // TODO: annotations for enum & enum itself
    private DemographicType targetDemographic;
    private boolean isSeawulf;
    private List<Districting> districtings;
    private List<BoxWhisker> boxWhiskerData;
    private int averageDistricting;
    private int extremeDistricting;

    /* Constructor */
    public Job() { }
    
    /* Properties */
    @Id
    @GeneratedValue(generator="jobIdSequence", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(
        name="jobIdSequence",
        sequenceName="JobIdSequence",
        allocationSize=10
    )
    @Column(name="ID", updatable=false)
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    public JobStatus getStatus() {
        return this.status;
    }
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name="stateID")
    public State getState() {
        return this.state;
    }
    public void setState(State state) {
        this.state = state;
    }

    @Column(name="numberDistrictings")
    public int getNumberDistrictings() {
        return this.numberDistrictings;
    }
    public void setNumberDistrictings(int numberDistrictings) {
        this.numberDistrictings = numberDistrictings;
    }

    @Column(name="compactnessAmount")
    public String getCompactnessAmount() {
        return this.compactnessAmount;
    }
    public void setCompactnessAmount(String compactnessAmount) {
        this.compactnessAmount = compactnessAmount;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="targetDemographic")
    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    @Column(name="isSeawulf")
    public boolean getIsSeawulf() {
        return this.isSeawulf;
    }
    public void setIsSeawulf(boolean isSeawulf) {
        this.isSeawulf = isSeawulf;
    }

    @OneToMany(mappedBy="job")
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

    @Column(name="averageDistricting")
    public int getAverageDistricting() {
        return this.averageDistricting;
    }
    public void setAverageDistricting(int averageDistricting) {
        this.averageDistricting = averageDistricting;
    }

    @Column(name="extremeDistricting")
    public int getExtremeDistricting() {
        return this.extremeDistricting;
    }
    public void setExtremeDistricting(int extremeDistricting) {
        this.extremeDistricting = extremeDistricting;
    }    

}