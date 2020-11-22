package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="county")
public class County {
    /* Fields */
    private int id;
    private String name;
    private State state;
    private String shape; // TODO: GEOJSON
    private Set<Precinct> precincts;

    /* Properties */
    @Id
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public State getState() {
        return this.state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public String getShape() {
        return this.shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }

    @OneToMany(mappedBy="county")
    public Set<Precinct> getPrecincts() {
        return this.precincts;
    }
    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

}