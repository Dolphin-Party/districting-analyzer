package com.party.dolphin.model;

import javax.persistence.*;

@Entity
@Table(name="PrecinctNeighbors")
public class PrecinctNeighbor {
    /* Fields */
    private PrecinctNeighborId id;
    private Precinct precinct;
    private Precinct neighbor;

    /* Properties */
    @EmbeddedId
    public PrecinctNeighborId getId() {
        return this.id;
    }
    public void setId(PrecinctNeighborId id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("precinctId")
    @JoinColumn(name = "precinctID")
    public Precinct getPrecinct() {
        return this.precinct;
    }
    public void setPrecinct(Precinct precinct) {
        this.precinct = precinct;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("neighborId")
    @JoinColumn(name = "neighborID")
    public Precinct getNeighbor() {
        return this.neighbor;
    }
    public void setNeighbor(Precinct neighbor) {
        this.neighbor = neighbor;
    }

}