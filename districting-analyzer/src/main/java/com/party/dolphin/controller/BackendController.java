package com.party.dolphin.controller;

@Controller
@RequestMapping(value = "/backend")
public class BackendController {

    @GetMapping
    public StateRender getState(int stateId) {
        return DatabaseService.findStateById(stateId);
    }
    public StateRender getStateByName(String stateName) {
        return DatabaseService.findStateByName(stateName);
    }
    public StateInfo getStateInfoByName(String stateName) {
        return DatabaseService.findStateByName(stateName);
    }
    public CountyRender getCounty(int countyId) {
        return DatabaseService.findCountyById(countyId);
    }
    public PrecinctRender getPrecinct(int precinctId) {
        return DatabaseService.findPrecinctById(precinctId);
    }
    public List<CountyRender> getStateCounties(int stateId) {
        return DatabaseService.findAllCounties(stateId);
    }
    public List<PrecinctRender> getStatePrecincts(int stateId) {
        return DatabaseService.findAllStatePrecincts(countyId);
    }
    public List<PrecinctRender> getCountyPrecincts(int countyId) {
        return DatabaseService.findAllCountyPrecincts(countyId);
    }
    public JobRender getJob(int jobId) {
        DatabaseService.findJobById(jobId);
    }
    public DistrictingRender getDistricting(int districtingId) {
        DatabaseService.findDistrictingById(districtingId);
    }
    public DistrictRender getDistrict(int districtId) {
        DatabaseService.findDistrictById(districtId);
    }
    public List<DistrictRender> getAllDistricts(int districtingId)
    {
        DatabaseService.findAllDistricts(districtingId);
    }
}