package com.party.dolphin.service;

import java.util.*;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountyRepository countyRepository;
    @Autowired
    private PrecinctRepository precinctRepository;

    public State getState(int id) {
        return stateRepository.findById(id);
    }

    public State getState(String name) {
        return stateRepository.findByName(name);
    }

    public County getCounty(int id) {
        return countyRepository.findById(id);
    }

    public List<County> getCountiesByState(int stateId) {
        return countyRepository.findAllByStateId(stateId);
    }

    public Precinct getPrecinct(int id) {
        return precinctRepository.findById(id);
    }

    public List<Precinct> getPrecinctsByState(int stateId) {
        List<County> counties = countyRepository.findAllByStateId(stateId);
        ArrayList<Precinct> statePrecincts = new ArrayList<Precinct>();
        for (County c : counties) {
            List<Precinct> countyPrecincts = precinctRepository.findAllByCountyId(c.getId());
            statePrecincts.addAll(countyPrecincts);
        }
        return statePrecincts;
    }

    public List<Precinct> getPrecinctsByCounty(int countyId) {
        return precinctRepository.findAllByCountyId(countyId);
    }

    // TODO: Implement
    public Set<Precinct> getPrecinctGraph(int stateId) {
        return new HashSet<Precinct>();
    }
}