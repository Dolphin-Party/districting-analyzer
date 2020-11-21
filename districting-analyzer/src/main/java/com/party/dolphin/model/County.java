package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="county")
public class County {
    /* Fields */
    private int countyId;
    private String name;
    private State state;
    private String shape; // TODO: GEOJSON
    private Set<Precinct> precincts;

    /* Getters */
    @Id
    public int getId() {
        return countyId;
    }

    public String getName() {
        return this.name;
    }

    public String getShape() {
        return this.shape;
    }

    @ManyToOne
    public State getState() {
        return this.state;
    }

    @OneToMany(mappedBy="county")
    public Set<Precinct> getPrecincts() {
        return this.precincts;
    }
}