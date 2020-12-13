package com.party.dolphin.model;

import com.party.dolphin.model.enums.DemographicType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Districtings")
public class Districting {
    /* Fields */
    private int id;
    private Job job;
    private DemographicType targetDemographic;
    private int districtingIndex;
    private List<District> districts;

    /* Properties */
    @Id
    @GeneratedValue(generator="districtingIdSequence", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(
        name="districtingIdSequence",
        sequenceName="DistrictingIdSequence",
        allocationSize=50
    )
    @Column(name="ID", updatable=false)
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="jobID")
    public Job getJob() {
        return this.job;
    }
    public void setJob(Job job) {
        this.job = job;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="targetDemographic")
    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    @Column(name="districtingIndex")
    public int getDistrictingIndex() {
        return this.districtingIndex;
    }
    public void setDistrictingIndex(int districtingIndex) {
        this.districtingIndex = districtingIndex;
    }

    @OneToMany(mappedBy="districting", cascade=CascadeType.ALL)
    public List<District> getDistricts() {
        return this.districts;
    }
    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    /* Server Processing */
    protected void calcNumberCounties() {
        for (District di : this.districts) {
            di.calcNumberCounties();
        }
    }

    protected void genOrderedDistricts(DemographicType demographic) {
        List<District> orderedDistricts = new ArrayList<District>(this.districts.size());
        double percentVap;
        for (District di : this.districts) {
            percentVap = di.computePercentVAP(demographic);
            di.setTargetDemographic(demographic);
            di.setTargetDemographicPercentVap(percentVap);
            orderedDistricts = insertOrderedDistrict(orderedDistricts, di);
        }
        this.setDistrictsOrder(orderedDistricts);
        this.districts = orderedDistricts;
    }

    private List<District> insertOrderedDistrict(List<District> list, District di) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTargetDemographicPercentVap() > di.getTargetDemographicPercentVap()) {
                list.add(i, di);
                return list;
            }
        }
        list.add(di);
        return list;
    }

    private void setDistrictsOrder(List<District> orderedList) {
        for (int i = 0; i < orderedList.size(); i++) {
            orderedList.get(i).setOrder(i+1);
        }
    }
}