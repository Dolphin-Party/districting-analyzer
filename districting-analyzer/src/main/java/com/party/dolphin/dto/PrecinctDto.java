package com.party.dolphin.dto;

import com.party.dolphin.model.enums.DemographicType;

import java.util.*;

public class PrecinctDto {
    /* Fields */
    private int precinctId;
    private int countyId;
    private String shape; // TODO: GEOJSON
    private Set<Integer> neighbors;
    private int population;
    private EnumMap<DemographicType, Integer> demographics;

    /* Getters */
    public int getPrecinctId() {
        return this.precinctId;
    }

    public int getCountyId() {
        return this.countyId;
    }

    public String getShape() {
        return this.shape;
    }

    public Set<Integer> getNeighbors() {
        return this.neighbors;
    }

    public int getPopulation() {
        return this.population;
    }

    public EnumMap<DemographicType,Integer> getDemographics() {
        return this.demographics;
    }

}