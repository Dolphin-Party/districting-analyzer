package com.party.dolphin.dto;

import java.util.Set;

public class StateDto {
    /* Fields */
    private int stateId;
    private String name;
    private int population;
    private Set<Integer> counties;
    private int canonicalDistrictingId;

    /* Properties */
    public int getStateId() {
        return this.stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public Set<Integer> getCounties() {
        return this.counties;
    }
    public void setCounties(Set<Integer> counties) {
        this.counties = counties;
    }

    public int getCanonicalDistrictingId() {
        return this.canonicalDistrictingId;
    }
    public void setCanonicalDistrictingId(int canonicalDistrictingId) {
        this.canonicalDistrictingId = canonicalDistrictingId;
    }

}