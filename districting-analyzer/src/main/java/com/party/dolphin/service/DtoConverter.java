package com.party.dolphin.service;

import java.util.stream.Collectors;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoConverter {

    @Autowired
    private DataService dataService;
    @Autowired
    private JobService jobService;

    public Districting createNewDistricting(DistrictingDto dto) {
        Districting districting = new Districting();
        districting = jobService.saveDistricting(districting);

        BeanUtils.copyProperties(dto, districting);
        districting.setJob(
            jobService.getJob(dto.getJobId())
        );
        //districting.setDistricts();
        return districting;
    }

    // Caller initializes entity object b/c id is auto-generated
    public District createNewDistrict(DistrictDto dto) {
        District district = new District();
        district = jobService.saveDistrict(district);

        BeanUtils.copyProperties(dto, district);
        district.setPrecincts(
            dto.getPrecincts().stream()
                .map(id -> dataService.getPrecinct(id))
                .collect(Collectors.toSet())
        );
        //district.setDistricting();
        return district;
    }
}
