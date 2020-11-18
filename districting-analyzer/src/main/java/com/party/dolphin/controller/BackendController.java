package com.party.dolphin.controller;

import com.party.dolphin.dto.*;
import com.party.dolphin.service.DatabaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend")
public class BackendController {

    @Autowired
    private DatabaseService dbService;

    @GetMapping("/state/{stateId}")
    public StateDto getState(@PathVariable int stateId) {
        return dbService.findStateById(stateId);
    }

    @GetMapping("/state/{stateName}")
    public StateDto getStateByName(@PathVariable String stateName) {
        return dbService.findStateByName(stateName);
    }

    @GetMapping("/state/{stateName}/info")
    public StateDto getStateInfoByName(@PathVariable String stateName) {
        return dbService.findStateByName(stateName);
    }

    @GetMapping("/county/{countyId}")
    public CountyDto getCounty(@PathVariable int countyId) {
        return dbService.findCountyById(countyId);
    }

    @GetMapping("/precinct/{precinctId}")
    public PrecinctDto getPrecinct(@PathVariable int precinctId) {
        return dbService.findPrecinctById(precinctId);
    }

    @GetMapping("/state/{stateId}/counties")
    public List<CountyDto> getStateCounties(@PathVariable int stateId) {
        return dbService.findAllCounties(stateId);
    }

    @GetMapping("/state/{stateId}/precincts")
    public List<PrecinctDto> getStatePrecincts(@PathVariable int stateId) {
        return dbService.findAllStatePrecincts(stateId);
    }

    @GetMapping("/county/{countyId}/precincts")
    public List<PrecinctDto> getCountyPrecincts(@PathVariable int countyId) {
        return dbService.findAllCountyPrecincts(countyId);
    }

    @GetMapping("/job/{jobId}")
    public JobDto getJob(@PathVariable int jobId) {
        return dbService.findJobById(jobId);
    }

    @GetMapping("/districting/{districtingId}")
    public DistrictingDto getDistricting(@PathVariable int districtingId) {
        return dbService.findDistrictingById(districtingId);
    }

    @GetMapping("/district/{districtId}")
    public DistrictDto getDistrict(@PathVariable int districtId) {
        return dbService.findDistrictById(districtId);
    }

    @GetMapping("/districting/{districtingId}/districts")
    public List<DistrictDto> getAllDistricts(@PathVariable int districtingId) {
        return dbService.findAllDistricts(districtingId);
    }
}
