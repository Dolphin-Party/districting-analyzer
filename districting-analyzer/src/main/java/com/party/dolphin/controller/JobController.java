package com.party.dolphin.controller;

import java.util.List;

import com.party.dolphin.dto.*;
import com.party.dolphin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO: Return DTO instead of model objects
@RestController
@RequestMapping(path="/backend")
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping(method=RequestMethod.POST, value="/job/init")
    public int addJob(@RequestBody JobDto job) {
        return jobService.addJob(job);
    }

    @RequestMapping(method=RequestMethod.GET, value="/job/{jobId}/status")
    public JobDto checkStatus(@PathVariable int jobId) {
        return jobService.getJobStatus(jobId);
    }

    @RequestMapping(method=RequestMethod.POST, value="/job/{jobId}/delete")
    public boolean deleteJob(@PathVariable int jobId) {
        return jobService.deleteJob(jobId);
    }
}