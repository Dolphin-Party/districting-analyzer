package com.party.dolphin.dto;

import java.util.Set;

public class CountyDto {
    /* Fields */
    private int countyId;
    private String name;
    private int stateId;
    private String shape; // TODO: GEOJSON
    private Set<Integer> precincts;

    /* Properties */
    public int getCountyId() {
        return this.countyId;
    }
    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getStateId() {
        return this.stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    public Set<Integer> getPrecincts() {
        return this.precincts;
    }
    public void setPrecincts(Set<Integer> precincts) {
        this.precincts = precincts;
    }

}