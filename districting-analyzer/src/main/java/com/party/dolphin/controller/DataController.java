package com.party.dolphin.controller;

import com.party.dolphin.dto.*;
import com.party.dolphin.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO: Return DTO instead of model objects
@RestController
@RequestMapping(path="/backend")
public class DataController {

    @Autowired
    private DataService dataService;
    @Autowired
    private JobService jobService;

    @GetMapping("/state/{stateId}")
    public StateDto getState(@PathVariable int stateId) {
        return dataService.getStateDto(stateId);
    }

    @GetMapping("/state/{stateName}")
    public StateDto getStateByName(@PathVariable String stateName) {
        return dataService.getStateDto(stateName);
    }

    @GetMapping("/state/all/info")
    public List<StateDto> getAllStates() {
        return dataService.getAllStateDtos();
    }

    // TODO: Redirect instead
    @GetMapping("/state/{stateName}/info")
    public StateDto getStateInfoByName(@PathVariable String stateName) {
        return dataService.getStateDto(stateName);
    }

    @GetMapping("/county/{countyId}")
    public CountyDto getCounty(@PathVariable int countyId) {
        return dataService.getCountyDto(countyId);
    }

    @GetMapping("/precinct/{precinctId}")
    public PrecinctDto getPrecinct(@PathVariable int precinctId) {
        return dataService.getPrecinctDto(precinctId);
    }

    @GetMapping("/state/{stateId}/counties")
    public List<CountyDto> getStateCounties(@PathVariable int stateId) {
        return dataService.getCountiesByState(stateId);
    }

    @GetMapping("/state/{stateId}/precincts")
    public List<PrecinctDto> getStatePrecincts(@PathVariable int stateId) {
        return dataService.getPrecinctsByState(stateId);
    }

    @GetMapping("/county/{countyId}/precincts")
    public List<PrecinctDto> getCountyPrecincts(@PathVariable int countyId) {
        return dataService.getPrecinctsByCounty(countyId);
    }

    @GetMapping("/job/{jobId}")
    public JobDto getJob(@PathVariable int jobId) {
        return jobService.getJobDto(jobId);
    }

    @GetMapping("/job/all")
    public List<JobDto> getAllJobs() {
        return jobService.getAllJobDtos();
    }

    @GetMapping("/districting/{districtingId}")
    public DistrictingDto getDistricting(@PathVariable int districtingId) {
        return jobService.getDistrictingDto(districtingId);
    }

    @GetMapping("/district/{districtId}")
    public DistrictDto getDistrict(@PathVariable int districtId) {
        return jobService.getDistrictDto(districtId);
    }

    @GetMapping("/districting/{districtingId}/districts")
    public List<DistrictDto> getDistrictingDistricts(@PathVariable int districtingId) {
        return jobService.getAllDistrictsByDistrictingId(districtingId);
    }
}
