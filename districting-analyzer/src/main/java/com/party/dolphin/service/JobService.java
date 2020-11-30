package com.party.dolphin.service;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    @Autowired
    private ModelConverter modelConverter;

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

    public JobDto getJob(int id) {
        Job job = jobRepository.findById(id);
        return modelConverter.createJobDto(job);
    }

    public List<JobDto> getJobsByState(int stateId) {
        List<Job> jobs = jobRepository.findAllByStateId(stateId);
        return jobs.stream()
            .map(j -> modelConverter.createJobDto(j))
            .collect(Collectors.toList());
    }

    public JobDto getJobStatus(int jobId) {
        Job job = jobRepository.findById(jobId);
        job = serverDispatcher.getJobStatus(job);
        job = jobRepository.save(job);
        return modelConverter.createJobDto(job);
    }

    public boolean deleteJob(int jobId) {
        Job job = jobRepository.findById(jobId);
        job = serverDispatcher.getJobStatus(job);
        if (job.getStatus() == JobStatus.running)
            job = serverDispatcher.cancelJob(job);
        jobRepository.delete(job);
        return true;
    }

    public DistrictingDto getDistrictingDto(int id) {
        Districting districting = districtingRepository.findById(id);
        return modelConverter.createDistrictingDto(districting);
    }

    public DistrictDto getDistrictDto(int id) {
        District district = districtRepository.findById(id);
        return modelConverter.createDistrictDto(district);
    }

    public List<DistrictDto> getAllDistrictsByDistrictingId(int districtingId) {
        List<District> districts = districtRepository.findAllByDistrictingId(districtingId);
        return districts.stream()
                        .map(d -> modelConverter.createDistrictDto(d))
                        .collect(Collectors.toList());
    }
}
