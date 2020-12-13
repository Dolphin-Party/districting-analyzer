package com.party.dolphin.service;

import java.util.*;
import java.util.stream.Collectors;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.projections.*;
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

    /** Fetch states **/
    public StateDto getStateDto(int id) {
        State state = stateRepository.findById(id);
        return modelConverter.createStateDto(state);
    }

    public StateDto getStateDto(String name) {
        State state = stateRepository.findByName(name);
        return modelConverter.createStateDto(state);
    }

    public List<StateDto> getAllStateDtos() {
        List<State> states = stateRepository.findAll();
        return states.stream()
                    .map(s -> modelConverter.createStateDto(s))
                    .collect(Collectors.toList());
    }

    /** Fetch counties **/
    public CountyDto getCountyDto(int id) {
        County county = countyRepository.findById(id);
        return modelConverter.createCountyDto(county);
    }

    public List<CountyDto> getCountiesByState(int stateId) {
        List<County> counties =
            countyRepository.findAllByStateId(stateId, County.class);
        return counties.stream()
                        .map(c -> modelConverter.createCountyDto(c))
                        .collect(Collectors.toList());
    }

    public List<Integer> getCountyIdsByState(int stateId) {
        List<CountyIdView> counties =
            countyRepository.findAllByStateId(stateId, CountyIdView.class);
        return counties.stream()
                        .map(c -> c.getId())
                        .collect(Collectors.toList());
    }

    /** Fetch precincts **/
    public PrecinctDto getPrecinctDto(String id) {
        Precinct precinct = precinctRepository.findById(id);
        return modelConverter.createPrecinctDto(precinct);
    }

    public List<PrecinctDto> getPrecinctsByState(int stateId) {
        List<Integer> countyIds = this.getCountyIdsByState(stateId);
        return countyIds.stream()
                        .map(id -> precinctRepository.findAllByCountyId(id, Precinct.class))
                        .flatMap(precincts -> precincts.stream())
                        .map(p -> modelConverter.createPrecinctDto(p))
                        .collect(Collectors.toList());
    }

    public List<String> getPrecinctIdsByState(int stateId) {
        List<Integer> countyIds = this.getCountyIdsByState(stateId);
        return countyIds.stream()
                        .map(id -> getPrecinctIdsByCounty(id))
                        .flatMap(precinctIds -> precinctIds.stream())
                        .collect(Collectors.toList());
    }

    public List<PrecinctDto> getPrecinctsByCounty(int countyId) {
        List<Precinct> precincts =
            precinctRepository.findAllByCountyId(countyId, Precinct.class);
        return precincts.stream()
                        .map(p -> modelConverter.createPrecinctDto(p))
                        .collect(Collectors.toList());
    }

    public List<String> getPrecinctIdsByCounty(int countyId) {
        List<PrecinctIdView> precincts =
            precinctRepository.findAllByCountyId(countyId, PrecinctIdView.class);
        return precincts.stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList());
    }

    /** Model accessors **/
    public State getState(int id) {
        return stateRepository.findById(id);
    }

    public Precinct getPrecinct(String id) {
        return precinctRepository.findById(id);
    }

    // TODO: Implement
    public Set<Precinct> getPrecinctGraph(int stateId) {
        return new HashSet<Precinct>();
    }
}