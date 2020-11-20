package com.party.dolphin.dto;

import java.util.Set;

public class CountyDto {
    /* Fields */
    private int countyId;
    private String name;
    private String shape; // TODO: GEOJSON
    private int stateId;
    private Set<Integer> precincts;

    /* Getters */
    public int getCountyId() {
        return this.countyId;
    }

    public String getName() {
        return this.name;
    }

    public String getShape() {
        return this.shape;
    }

    public int getStateId() {
        return this.stateId;
    }

    public Set<Integer> getPrecincts() {
        return this.precincts;
    }

}