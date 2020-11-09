package com.party.dolphin.controller;

@Controller
@RequestMapping(value = "/backend")
public class BackendController {
    private DatabaseService dbService;

    public BackendController(DatabaseService dbService) {
        this.dbService = dbService;
    }
ls
    public StateRender getState(int stateId) {
        return dbService.findStateById(stateId);
    }
    public StateRender getStateByName(String stateName) {
        return dbService.findStateByName(stateName);
    }
    public StateInfo getStateInfoByName(String stateName) {
        return dbService.findStateByName(stateName);
    }
    public CountyRender getCounty(int countyId) {
        return dbService.findCountyById(countyId);
    }
    public PrecinctRender getPrecinct(int precinctId) {
        return dbService.findPrecinctById(precinctId);
    }
    public List<CountyRender> getStateCounties(int stateId) {
        return dbService.findAllCounties(stateId);
    }
    public List<PrecinctRender> getStatePrecincts(int stateId) {
        return dbService.findAllStatePrecincts(countyId);
    }
    public List<PrecinctRender> getCountyPrecincts(int countyId) {
        return dbService.findAllCountyPrecincts(countyId);
    }
    public JobRender getJob(int jobId) {
        return dbService.findJobById(jobId);
    }
    public DistrictingRender getDistricting(int districtingId) {
        return dbService.findDistrictingById(districtingId);
    }
    public DistrictRender getDistrict(int districtId) {
        return dbService.findDistrictById(districtId);
    }
    public List<DistrictRender> getAllDistricts(int districtingId)
    {
        return dbService.findAllDistricts(districtingId);
    }
}
