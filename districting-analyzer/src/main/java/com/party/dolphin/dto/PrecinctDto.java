package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.*;

public class PrecinctDto {
    /* Fields */
    private String precinctId;
    private int countyId;
    private String shape;
    private Set<String> neighbors;
    private int population;
    private Map<DemographicType, Integer> demographics;

    /* Properties */
    public String getPrecinctId() {
        return this.precinctId;
    }
    public void setPrecinctId(String precinctId) {
        this.precinctId = precinctId;
    }

    public int getCountyId() {
        return this.countyId;
    }
    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    public Set<String> getNeighbors() {
        return this.neighbors;
    }
    public void setNeighbors(Set<String> neighbors) {
        this.neighbors = neighbors;
    }

    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public Map<DemographicType,Integer> getDemographics() {
        return this.demographics;
    }
    public void setDemographics(EnumMap<DemographicType,Integer> demographics) {
        this.demographics = demographics;
    }

}