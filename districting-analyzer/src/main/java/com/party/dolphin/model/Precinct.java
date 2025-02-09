package com.party.dolphin.model;

import com.party.dolphin.model.enums.DemographicType;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
@Table(name="Precincts")
public class Precinct {
    /* Fields */
    private String id;
    private County county;
    private String shape;
    private Set<PrecinctNeighbor> neighborsLower;
    private Set<PrecinctNeighbor> neighborsUpper;
    private int population;
    private List<PrecinctDemographic> demographics;

    /* Properties */
    @Id
    @Column(name="ID", updatable=false)
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="countyID")
    public County getCounty() {
        return this.county;
    }
    public void setCounty(County county) {
        this.county = county;
    }

    @Column(name="shape", columnDefinition="JSON")
    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    @OneToMany(mappedBy="precinct")
    public Set<PrecinctNeighbor> getNeighborsLower() {
        return this.neighborsLower;
    }
    public void setNeighborsLower(Set<PrecinctNeighbor> neighborsLower) {
        this.neighborsLower = neighborsLower;
    }

    @OneToMany(mappedBy="neighbor")
    public Set<PrecinctNeighbor> getNeighborsUpper() {
        return this.neighborsUpper;
    }
    public void setNeighborsUpper(Set<PrecinctNeighbor> neighborsUpper) {
        this.neighborsUpper = neighborsUpper;
    }

    @Column(name="population")
    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    @OneToMany(mappedBy="precinct", fetch=FetchType.EAGER)
    public List<PrecinctDemographic> getDemographics() {
        return this.demographics;
    }
    public void setDemographics(List<PrecinctDemographic> demographics) {
        this.demographics = demographics;
    }

    /* Other Methods */
    @Transient
    public Set<Precinct> getNeighbors() {
        int capacity = this.neighborsLower.size() + this.neighborsUpper.size();
        Set<Precinct> allNeighbors = new HashSet<Precinct>(capacity);
        allNeighbors.addAll(
            this.neighborsLower.stream()
                .map(n -> n.getNeighbor())
                .collect(Collectors.toSet())
        );
        allNeighbors.addAll(
            this.neighborsUpper.stream()
                .map(n -> n.getPrecinct())
                .collect(Collectors.toSet())
        );
        return allNeighbors;
    }

    @Transient
    public int getVAP(DemographicType demographic) {
        return this.demographics.stream()
                    .filter(d -> d.getDemographic() == demographic)
                    .findFirst()
                    .orElse(new PrecinctDemographic())
                    .getPopulation();
    }
}