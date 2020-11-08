package com.party.dolphin.model;

import java.util.Set;

import javax.persistence.Id;

@Entity
public class State {
    private @Id int stateId;
    private String name;
    private Set<County> counties;
    private Districting canonicalDistricting;
}