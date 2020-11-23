package com.party.dolphin.service;

import java.util.List;
import java.util.Set;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.model.enums.JobStatus;
import com.party.dolphin.repository.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired
    private DataService dataService;
    @Autowired
    private ServerDispatcher serverDispatcher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private DistrictingRepository districtingRepository;
    @Autowired
    private DistrictRepository districtRepository;

    public int addJob(JobDto jobDto) {
        Job job = new Job();
        BeanUtils.copyProperties(jobDto, job);
        job.setStatus(JobStatus.notStarted);
        State state = dataService.getState(jobDto.getStateId());
        job.setState(state);
        job = jobRepository.save(job);

        job = serverDispatcher.runJob(job);
        job = jobRepository.save(job);
        return job.getId();
    }

    public Job getJob(int id) {
        return jobRepository.findById(id);
    }

    public List<Job> getJobsByState(int stateId) {
        return jobRepository.findAllByStateId(stateId);
    }

    public Job getJobStatus(int jobId) {
        Job job = getJob(jobId);
        job = serverDispatcher.getJobStatus(job);
        job = jobRepository.save(job);
        return job;
    }

    public boolean deleteJob(int jobId) {
        Job job = getJobStatus(jobId);
        if (job.getStatus() == JobStatus.running)
            job = serverDispatcher.cancelJob(job);
        jobRepository.delete(job);
        return true;
    }

    public Districting getDistricting(int id) {
        return districtingRepository.findById(id);
    }

    public District getDistrict(int id) {
        return districtRepository.findById(id);
    }

    public List<District> getAllDistrictsByDistrictingId(int districtingId) {
        return districtRepository.findAllByDistrictingId(districtingId);
    }
}
