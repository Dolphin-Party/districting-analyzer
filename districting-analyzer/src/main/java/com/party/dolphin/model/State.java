package com.party.dolphin.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="States")
public class State {
    /* Fields */
    private int id;
    private String name;
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

    @OneToMany(mappedBy="state")
    public Set<County> getCounties() {
        return this.counties;
    }
    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }

    @OneToOne
    @JoinColumn(name="canonicalDistrictingID")
    public Districting getCanonicalDistricting() {
        return this.canonicalDistricting;
    }
    public void setCanonicalDistricting(Districting canonicalDistricting) {
        this.canonicalDistricting = canonicalDistricting;
    }

    /* Other Methods */
    @Transient
    public int getPopulation() {
        return counties.stream()
                        .mapToInt(c -> c.getPopulation())
                        .sum();
    }

}