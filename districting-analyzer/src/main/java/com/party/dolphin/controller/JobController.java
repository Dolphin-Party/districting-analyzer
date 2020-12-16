package com.party.dolphin.controller;

import java.io.File;

import com.party.dolphin.dto.*;
import com.party.dolphin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

// TODO: Return DTO instead of model objects
@RestController
@RequestMapping(path="/backend")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping(value="/job/init")
    public int addJob(@RequestBody JobDto job) {
        return jobService.addJob(job);
    }

    @GetMapping(value="/job/{jobId}/status")
    public JobDto checkStatus(@PathVariable int jobId) {
        return jobService.getJobStatus(jobId);
    }

    @PostMapping(value="/job/{jobId}/cancel")
    public boolean cancelJob(@PathVariable int jobId) {
        return jobService.deleteJob(jobId);
    }

    @PostMapping(value="/job/{jobId}/delete")
    public boolean deleteJob(@PathVariable int jobId) {
        return jobService.deleteJob(jobId);
    }

    @GetMapping(value="/job/{jobId}/summary")
    public @ResponseBody FileSystemResource getSummaryFile(@PathVariable int jobId) {
        File file = jobService.getSummaryFile(jobId);
        if (file == null)
            return null;
        else
            return new FileSystemResource(file);
    }

    // Debug
    @PostMapping(value="/job/{jobId}/genSummary")
    public boolean genSummaryFile(@PathVariable int jobId) {
        return jobService.genSummaryFile(jobId);
    }
}