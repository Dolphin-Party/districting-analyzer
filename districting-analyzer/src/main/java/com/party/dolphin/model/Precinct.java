package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.*;

import javax.persistence.Id;

public class Precinct {
    /* Fields */
    private @Id int precinctId;
    private County county;
    private String shape; // TODO: GEOJSON
    private Set<Precinct> neighbors;
    private int population;
    private EnumMap<DemographicType, Integer> demographics;

    /* Getters */
    public int getPrecinctId() {
        return this.precinctId;
    }

    public County getCounty() {
        return this.county;
    }

    public String getShape() {
        return this.shape;
    }

    public Set<Precinct> getNeighbors() {
        return this.neighbors;
    }

    public int getPopulation() {
        return this.population;
    }

    public EnumMap<DemographicType,Integer> getDemographics() {
        return this.demographics;
    }
}