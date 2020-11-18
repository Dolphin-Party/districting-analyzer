package com.party.dolphin.dto;

import java.util.Set;

public class StateDto {
    /* Fields */
    private int stateId;
    private String name;
    private int population;
    private Set<Integer> counties;
    private String canonicalDistricting;

    /* Getters */
    public int getStateId() {
        return this.stateId;
    }

    public String getName() {
        return this.name;
    }

    public int getPopulation() {
        return this.population;
    }

    public Set<Integer> getCounties() {
        return this.counties;
    }

    public String getCanonicalDistricting() {
        return this.canonicalDistricting;
    }

    /* Getters */
    /* TODO: Add constructor and delete setters */
	public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    public void setCounties(Set<Integer> counties) {
        this.counties = counties;
    }
    public void setCanonicalDistricting(String canonicalDistricting) {
        this.canonicalDistricting = canonicalDistricting;
    }

}