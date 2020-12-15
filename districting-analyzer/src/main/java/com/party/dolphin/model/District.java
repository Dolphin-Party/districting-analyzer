package com.party.dolphin.model;

import com.party.dolphin.model.enums.DemographicType;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="Districts")
public class District implements Comparable<District> {
    /* Fields */
    private int id;
    private Districting districting;
    private Set<Precinct> precincts;
    private int numberCounties;
    private DemographicType targetDemographic;
    private double targetDemographicPercentVap;
    private int order;

    /* Properties */
    @Id
    @GeneratedValue(generator="districtIdSequence", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(
        name="districtIdSequence",
        sequenceName="DistrictIdSequence",
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
    @JoinColumn(name="districtingID")
    public Districting getDistricting() {
        return this.districting;
    }
    public void setDistricting(Districting districting) {
        this.districting = districting;
    }

    @ManyToMany
    @JoinTable(
        name="DistrictPrecincts",
        joinColumns={
            @JoinColumn(name="districtID", referencedColumnName="ID", nullable=false)},
        inverseJoinColumns={
            @JoinColumn(name="precinctID", referencedColumnName="ID", nullable=false)})
    public Set<Precinct> getPrecincts() {
        return this.precincts;
    }
    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    @Column(name="numberCounties")
    public int getNumberCounties() {
        return this.numberCounties;
    }
    public void setNumberCounties(int numberCounties) {
        this.numberCounties = numberCounties;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="targetDemographic")
    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    @Column(name="targetDemographicPercentVap")
    public double getTargetDemographicPercentVap() {
        return this.targetDemographicPercentVap;
    }
    public void setTargetDemographicPercentVap(double targetDemographicPercentVap) {
        this.targetDemographicPercentVap = targetDemographicPercentVap;
    }

    @Column(name="`order`")
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    /* Server Processing */
    protected void calcNumberCounties() {
        HashSet<County> countySet = new HashSet<County>();
        for (Precinct p : this.precincts) {
            County c = p.getCounty();
            countySet.add(c);
        }
        this.setNumberCounties(countySet.size());
    }

    protected double computePercentVAP(DemographicType demographic) {
        int totalVAP = 0;
        int totalPopulation = 0;
        for (Precinct p : this.precincts) {
            totalVAP += p.getVAP(demographic);
            totalPopulation += p.getPopulation();
        }
        return totalVAP / ((double)totalPopulation);
    }

    @Override
    public int compareTo(District district) {
        if (this.targetDemographicPercentVap < district.getTargetDemographicPercentVap())
            return -1;
        else if (this.targetDemographicPercentVap > district.getTargetDemographicPercentVap())
            return 1;
        else
            return 0;
    }
}