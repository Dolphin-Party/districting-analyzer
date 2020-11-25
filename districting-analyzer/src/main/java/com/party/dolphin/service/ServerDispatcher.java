package com.party.dolphin.service;

import com.party.dolphin.model.*;
import com.party.dolphin.model.enums.JobStatus;

import org.springframework.stereotype.Service;

@Service
public class ServerDispatcher {
    public Job runJob(Job job) {
        job.setStatus(JobStatus.running);
        return job;
    }

    public Job getJobStatus(Job job) {
        if (job.getStatus() == JobStatus.finishDistricting)
            job.analyzeJobResults();
        return job;
    }

    public Job cancelJob(Job job) {
        job.setStatus(JobStatus.stopped);
        return job;
    }
}