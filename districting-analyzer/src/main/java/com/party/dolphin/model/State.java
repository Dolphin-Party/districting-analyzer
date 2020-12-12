package com.party.dolphin.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="States")
public class State {
    /* Fields */
    private int id;
    private String name;
    private int population;
    private String shape;
    private Set<County> counties;
    private Districting canonicalDistricting;

    /* Properties */
    @Id
    @Column(name="ID", updatable=false)
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="population")
    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    @OneToMany(mappedBy="state")
    public Set<County> getCounties() {
        return this.counties;
    }
    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }

    @Column(name="shape", columnDefinition="JSON")
    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    @OneToOne
    @JoinColumn(name="canonicalDistrictingID")
    public Districting getCanonicalDistricting() {
        return this.canonicalDistricting;
    }
    public void setCanonicalDistricting(Districting canonicalDistricting) {
        this.canonicalDistricting = canonicalDistricting;
    }

}