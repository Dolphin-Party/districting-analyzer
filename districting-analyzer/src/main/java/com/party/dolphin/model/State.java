package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.Set;

import javax.persistence.Id;

@Entity
public class State {
    /* Fields */
    private @Id int stateId;
    private String name;
    private Set<County> counties;
    private Districting canonicalDistricting;

    /* Getters */
    public int getStateId() {
        return this.stateId;
    }

    public String getName() {
        return this.name;
    }

    public Set<County> getCounties() {
        return this.counties;
    }

    public Districting getCanonicalDistricting() {
        return this.canonicalDistricting;
    }

    /* Setters */
    /* TODO: Add constructor and delete setters */
	public void setStateId(int stateId) {
        this.stateId = stateId;
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