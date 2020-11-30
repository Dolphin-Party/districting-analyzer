package com.party.dolphin.service;

import java.util.*;
import java.util.stream.Collectors;

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
        State state = stateRepository.findById(id);
        return modelConverter.createStateDto(state);
    }

    public StateDto getStateDto(String name) {
        State state = stateRepository.findByName(name);
        return modelConverter.createStateDto(state);
    }

    public CountyDto getCountyDto(int id) {
        County county = countyRepository.findById(id);
        return modelConverter.createCountyDto(county);
    }

    public List<CountyDto> getCountiesByState(int stateId) {
        List<County> counties = countyRepository.findAllByStateId(stateId);
        return counties.stream()
                        .map(c -> modelConverter.createCountyDto(c))
                        .collect(Collectors.toList());
    }

    public PrecinctDto getPrecinctDto(int id) {
        Precinct precinct = precinctRepository.findById(id);
        return modelConverter.createPrecinctDto(precinct);
    }

    public List<PrecinctDto> getPrecinctsByState(int stateId) {
        List<County> counties = countyRepository.findAllByStateId(stateId);
        return counties.stream()
                        .map(c -> c.getId())
                        .map(id -> precinctRepository.findAllByCountyId(id))
                        .flatMap(precincts -> precincts.stream())
                        .map(p -> modelConverter.createPrecinctDto(p))
                        .collect(Collectors.toList());
    }

    public List<PrecinctDto> getPrecinctsByCounty(int countyId) {
        List<Precinct> precincts = precinctRepository.findAllByCountyId(countyId);
        return precincts.stream()
                        .map(p -> modelConverter.createPrecinctDto(p))
                        .collect(Collectors.toList());
    }

    // TODO: Implement
    public Set<Precinct> getPrecinctGraph(int stateId) {
        return new HashSet<Precinct>();
    }
}