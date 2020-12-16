package com.party.dolphin.service;

import java.io.File;
import java.util.List;
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

    /** Job Control **/
    public int addJob(JobDto jobDto) {
        Job job = new Job();
        if (jobDto.getIterations() == 0)
            BeanUtils.copyProperties(jobDto, job, "iterations");
        else {
            BeanUtils.copyProperties(jobDto, job);
        }
        job.setStatus(JobStatus.notStarted);
        State state = dataService.getState(jobDto.getStateId());
        job.setState(state);
        job = jobRepository.save(job);

        job = serverDispatcher.runJob(job);
        job = jobRepository.save(job);
        return job.getId();
    }

    public JobDto getJobStatus(int jobId) {
        Job job = jobRepository.findById(jobId);
        job = serverDispatcher.checkJobStatus(job);
        job = jobRepository.save(job);
        return modelConverter.createJobDto(job);
    }

    // public boolean cancelJob(int jobId) {
    //     Job job = jobRepository.findById(jobId);
    //     job = serverDispatcher.checkJobStatus(job);
    //     if (job.getStatus() == JobStatus.running)
    //         job = serverDispatcher.cancelJob(job);
    //     job = jobRepository.save(job);
    //     return true;
    // }

    public boolean deleteJob(int jobId) {
        Job job = jobRepository.findById(jobId);
        if (job == null)
            return false;
        job = serverDispatcher.checkJobStatus(job);
        if (job.getStatus() == JobStatus.running)
            job = serverDispatcher.cancelJob(job);
        jobRepository.delete(job);
        return true;
    }

    /** Get entities and dtos **/
    public Job getJob(int id) {
        return jobRepository.findById(id);
    }

    public JobDto getJobDto(int id) {
        Job job = jobRepository.findById(id);
        return modelConverter.createJobDto(job);
    }

    public List<JobDto> getAllJobDtos() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                    .map(j -> modelConverter.createJobDto(j))
                    .collect(Collectors.toList());
    }

    public List<JobDto> getJobsByState(int stateId) {
        List<Job> jobs = jobRepository.findAllByStateId(stateId);
        return jobs.stream()
                    .map(j -> modelConverter.createJobDto(j))
                    .collect(Collectors.toList());
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

    /** Get Summary File **/
    public File getSummaryFile(int jobId) {
        Job job = jobRepository.findById(jobId);
        if (job.getStatus() != JobStatus.finishProcessing)
            return null;
        return serverDispatcher.getSummaryFile(job);
    }

    /** Save entities **/
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public Districting saveDistricting(Districting districting) {
        return districtingRepository.save(districting);
    }

    public District saveDistrict(District district) {
        return districtRepository.save(district);
    }

    /** Debug **/
    public boolean readFileTest(int jobId) {
        Job job = jobRepository.findById(jobId);
        job = serverDispatcher.readOutputFiles(job);
        return true;
    }

    public int createDistrict(DistrictDto dto) {
        District district = new District();
        district = districtRepository.save(district);
        return district.getId();
    }

    public boolean genSummaryFile(int jobId) {
        Job job = jobRepository.findById(jobId);
        return serverDispatcher.generateSummaryFile(job);
    }
}
