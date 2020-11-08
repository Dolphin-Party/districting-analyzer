package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.Set;

import javax.persistence.Id;

public class County {
    /* Fields */
    private @Id int countyId;
    private String name;
    private String shape; // TODO: GEOJSON
    private State state;
    private Set<Precinct> precincts;

    /* Getters */
    public int getCountyId() {
        return this.countyId;
    }

    public String getName() {
        return this.name;
    }

    public String getShape() {
        return this.shape;
    }

    public State getState() {
        return this.state;
    }

    public Set<Precinct> getPrecincts() {
        return this.precincts;
    }
}