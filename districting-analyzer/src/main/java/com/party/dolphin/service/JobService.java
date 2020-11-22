package com.party.dolphin.service;

import java.util.List;
import java.util.Set;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private DistrictingRepository districtingRepository;
    @Autowired
    private DistrictRepository districtRepository;


    public Job getJob(int id) {
        return jobRepository.findById(id);
    }

    public List<Job> getJobsByState(int stateId) {
        return jobRepository.findAllByStateId(stateId);
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
