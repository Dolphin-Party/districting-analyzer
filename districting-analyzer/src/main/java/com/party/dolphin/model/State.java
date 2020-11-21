package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="state")
public class State {
    /* Fields */
    private int id;
    private String name;
    private Set<County> counties;
    private Districting canonicalDistricting;

    /* Getters */
    @Id
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @OneToMany(mappedBy="state")
    public Set<County> getCounties() {
        return this.counties;
    }

    @OneToOne
    public Districting getCanonicalDistricting() {
        return this.canonicalDistricting;
    }

    /* Setters */
    /* TODO: Add constructor and delete setters */
	public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }
    public void setCanonicalDistricting(Districting canonicalDistricting) {
        this.canonicalDistricting = canonicalDistricting;
    }

}