package com.party.dolphin.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="Counties")
public class County {
    /* Fields */
    private int id;
    private String name;
    private State state;
    private int population;
    private String shape;
    private Set<Precinct> precincts;

    /* Properties */
    @Id
    @Column(name="ID", updatable=false)
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="population")
    public int getPopulation() {
        return this.population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stateID")
    public State getState() {
        return this.state;
    }
    public void setState(State state) {
        this.state = state;
    }

    @Column(name="shape", columnDefinition="JSON")
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