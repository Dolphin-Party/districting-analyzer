package com.party.dolphin.model;

import com.party.dolphin.model.enums.DemographicType;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="Precincts")
public class Precinct {
    /* Fields */
    private String id;
    private County county;
    private String shape;
    private Set<Precinct> neighbors;
    private int population;
    private Map<DemographicType, Integer> demographics;

    /* Properties */
    @Id
    @Column(name="ID", updatable=false)
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
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

    @ManyToMany
    @JoinTable(name="PrecinctNeighbors")
    public Set<Precinct> getNeighbors() {
        return this.neighbors;
    }
    public void setNeighbors(Set<Precinct> neighbors) {
        this.neighbors = neighbors;
    }

    @Column(name="population")
    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    @ElementCollection
    @CollectionTable(
        name="PrecinctDemographics",
        joinColumns=@JoinColumn(name="precinctID")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="demographic")
    @Column(name="population")
    public Map<DemographicType,Integer> getDemographics() {
        return this.demographics;
    }
    public void setDemographics(Map<DemographicType,Integer> demographics) {
        this.demographics = demographics;
    }

    /* Other Methods */
    public int getVAP(DemographicType demographic) {
        return this.demographics.get(demographic);
    }
}