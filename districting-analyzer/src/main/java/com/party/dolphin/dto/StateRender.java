package com.party.dolphin.dto;

import java.util.Set;

import javax.persistence.Id;

public class StateRender {
    /* Fields */
    private @Id int stateId;
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
}