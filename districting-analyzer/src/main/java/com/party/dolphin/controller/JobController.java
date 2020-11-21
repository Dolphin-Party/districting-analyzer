package com.party.dolphin.controller;

import java.util.List;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO: Return DTO instead of model objects
@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping(method=RequestMethod.POST, value="/job/init")
    public String addJob(@RequestBody JobDto job) {
        return "Hello";
    }
    
    @GetMapping("/job/{jobId}")
    public Job getJob(@PathVariable int jobId) {
        return jobService.getJob(jobId);
    }

    @GetMapping("/districting/{districtingId}")
    public Districting getDistricting(@PathVariable int districtingId) {
        return jobService.getDistricting(districtingId);
    }

    @GetMapping("/district/{districtId}")
    public District getDistrict(@PathVariable int districtId) {
        return jobService.getDistrict(districtId);
    }

    @GetMapping("/districting/{districtingId}/districts")
    public List<District> getDistrictingDistricts(@PathVariable int districtingId) {
        return jobService.getAllDistrictsByDistrictingId(districtingId);
    }
}