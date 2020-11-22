package com.party.dolphin.model;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.enums.DemographicType;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="precinct")
public class Precinct {
    /* Fields */
    private int id;
    private County county;
    private String shape; // TODO: GEOJSON
    private Set<Precinct> neighbors;
    private int population;
    private EnumMap<DemographicType, Integer> demographics;

    /* Properties */
    @Id
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public County getCounty() {
        return this.county;
    }
    public void setCounty(County county) {
        this.county = county;
    }

    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    @ManyToMany
    public Set<Precinct> getNeighbors() {
        return this.neighbors;
    }
    public void setNeighbors(Set<Precinct> neighbors) {
        this.neighbors = neighbors;
    }

    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public EnumMap<DemographicType,Integer> getDemographics() {
        return this.demographics;
    }
    public void setDemographics(EnumMap<DemographicType,Integer> demographics) {
        this.demographics = demographics;
    }

}