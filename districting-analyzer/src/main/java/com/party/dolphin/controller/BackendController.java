package com.party.dolphin.controller;

import com.party.dolphin.dto.StateRender;
import com.party.dolphin.service.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/backend")
public class BackendController {
    private DatabaseService dbService;

    public BackendController(DatabaseService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/state/{stateId}")
    public StateRender getState(@PathVariable int stateId) {
        return dbService.findStateById(stateId);
    }

    @GetMapping("/state/{stateName}")
    public StateRender getStateByName(@PathVariable String stateName) {
        return dbService.findStateByName(stateName);
    }

    @GetMapping("/state/{stateName}/info")
    public StateInfo getStateInfoByName(@PathVariable String stateName) {
        return dbService.findStateByName(stateName);
    }

    @GetMapping("/county/{countyId}")
    public CountyRender getCounty(@PathVariable int countyId) {
        return dbService.findCountyById(countyId);
    }

    @GetMapping("/precinct/{precinctId}")
    public PrecinctRender getPrecinct(@PathVariable int precinctId) {
        return dbService.findPrecinctById(precinctId);
    }

    @GetMapping("/state/{stateId}/counties")
    public List<CountyRender> getStateCounties(@PathVariable int stateId) {
        return dbService.findAllCounties(stateId);
    }

    @GetMapping("/state/{stateId}/precincts")
    public List<PrecinctRender> getStatePrecincts(@PathVariable int stateId) {
        return dbService.findAllStatePrecincts(countyId);
    }

    @GetMapping("/county/{countyId}/precincts")
    public List<PrecinctRender> getCountyPrecincts(@PathVariable int countyId) {
        return dbService.findAllCountyPrecincts(countyId);
    }

    @GetMapping("/job/{jobId}")
    public JobRender getJob(@PathVariable int jobId) {
        return dbService.findJobById(jobId);
    }

    @GetMapping("/districting/{districtingId}")
    public DistrictingRender getDistricting(@PathVariable int districtingId) {
        return dbService.findDistrictingById(districtingId);
    }

    @GetMapping("/district/{districtId}")
    public DistrictRender getDistrict(@PathVariable int districtId) {
        return dbService.findDistrictById(districtId);
    }

    @GetMapping("/districting/{districtingId}/districts")
    public List<DistrictRender> getAllDistricts(@PathVariable int districtingId) {
        return dbService.findAllDistricts(districtingId);
    }
}
