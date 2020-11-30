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
    @Autowired
    private ModelConverter modelConverter;

    public StateDto getStateDto(int id) {
        State state = this.getState(id);
        return modelConverter.createStateDto(state);
    }

    public StateDto getStateDto(String name) {
        State state = stateRepository.findByName(name);
        return modelConverter.createStateDto(state);
    }

    public CountyDto getCountyDto(int id) {
        County county = this.getCounty(id);
        return modelConverter.createCountyDto(county);
    }

    public List<County> getCountiesByState(int stateId) {
        return countyRepository.findAllByStateId(stateId);
    }

    public PrecinctDto getPrecinctDto(int id) {
        Precinct precinct = precinctRepository.findById(id);
        return modelConverter.createPrecinctDto(precinct);
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

    /* Model accessors */
    public State getState(int id) {
        return stateRepository.findById(id);
    }

    public County getCounty(int id) {
        return countyRepository.findById(id);
    }
}