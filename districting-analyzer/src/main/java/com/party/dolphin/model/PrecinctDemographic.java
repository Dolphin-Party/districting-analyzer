package com.party.dolphin.model;

import javax.persistence.*;

import com.party.dolphin.model.enums.DemographicType;

@Entity
@Table(name="PrecinctDemographics")
public class PrecinctDemographic {
    /* Fields */
    private PrecinctDemographicPK id;
    private Precinct precinct;
    private DemographicType demographic;
    private int population;

    /* Properties */
    @EmbeddedId
    public PrecinctDemographicPK getId() {
        return this.id;
    }
    public void setId(PrecinctDemographicPK id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "precinctID", nullable=false, insertable=false, updatable=false)
    public Precinct getPrecinct() {
        return this.precinct;
    }
    public void setPrecinct(Precinct precinct) {
        this.precinct = precinct;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="demographic", nullable=false, insertable=false, updatable=false)
    public DemographicType getDemographic() {
        return this.demographic;
    }
    public void setDemographic(DemographicType demographic) {
        this.demographic = demographic;
    }

    @Column(name="population")
    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            PrecinctDemographicPK id = new PrecinctDemographicPK();
            id.setPrecinctId(this.precinct.getId());
            id.setDemographic(this.demographic.toString());
            this.id = id;
        }
    }

}