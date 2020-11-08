package com.party.dolphin.model;

import com.party.dolphin.dto.*;

import java.util.Set;

import javax.persistence.Id;

public class State {
    /* Fields */
    private @Id int stateId;
    private String name;
    private Set<County> counties;
    private Districting canonicalDistricting;

    /* Getters */
    public int getStateId() {
        return this.stateId;
    }

    public String getName() {
        return this.name;
    }

    public Set<County> getCounties() {
        return this.counties;
    }

    public Districting getCanonicalDistricting() {
        return this.canonicalDistricting;
    }    
}