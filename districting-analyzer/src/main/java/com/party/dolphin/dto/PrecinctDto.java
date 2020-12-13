package com.party.dolphin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.party.dolphin.model.enums.DemographicType;

import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(Include.NON_NULL)
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
    public void setDemographics(Map<DemographicType,Integer> demographics) {
        this.demographics = demographics;
    }

    /* Other Methods */
    @JsonIgnore
    public List<PrecinctNeighborDto> getEdges() {
        return this.neighbors.stream()
                    .map(n -> new PrecinctNeighborDto(this.precinctId, n))
                    .collect(Collectors.toList());
    }

}