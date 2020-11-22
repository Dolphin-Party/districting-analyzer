package com.party.dolphin.controller;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.service.DataService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO: Return DTO instead of model objects
@Controller
@RequestMapping(path="/backend")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/state/{stateId}")
    public State getState(@PathVariable int stateId) {
        return dataService.getState(stateId);
    }

    @GetMapping("/state/{stateName}")
    public State getStateByName(@PathVariable String stateName) {
        return dataService.getState(stateName);
    }

    // TODO: Redirect instead
    @GetMapping("/state/{stateName}/info")
    public State getStateInfoByName(@PathVariable String stateName) {
        return dataService.getState(stateName);
    }

    @GetMapping("/county/{countyId}")
    public County getCounty(@PathVariable int countyId) {
        return dataService.getCounty(countyId);
    }

    @GetMapping("/precinct/{precinctId}")
    public Precinct getPrecinct(@PathVariable int precinctId) {
        return dataService.getPrecinct(precinctId);
    }

    @GetMapping("/state/{stateId}/counties")
    public List<County> getStateCounties(@PathVariable int stateId) {
        return dataService.getCountiesByState(stateId);
    }

    @GetMapping("/state/{stateId}/precincts")
    public List<Precinct> getStatePrecincts(@PathVariable int stateId) {
        return dataService.getPrecinctsByState(stateId);
    }

    @GetMapping("/county/{countyId}/precincts")
    public List<Precinct> getCountyPrecincts(@PathVariable int countyId) {
        return dataService.getPrecinctsByCounty(countyId);
    }
}
