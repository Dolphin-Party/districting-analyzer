package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.DemographicType;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="precinct")
public class Precinct {
    /* Fields */
    private int precinctId;
    private County county;
    private String shape; // TODO: GEOJSON
    private Set<Precinct> neighbors;
    private int population;
    private EnumMap<DemographicType, Integer> demographics;

    /* Getters */
    @Id
    public int getPrecinctId() {
        return this.precinctId;
    }

    @ManyToOne
    public County getCounty() {
        return this.county;
    }

    public String getShape() {
        return this.shape;
    }

    @ManyToMany
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