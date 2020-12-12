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

    public Districting createDistricting(DistrictingDto dto) {
        Districting districting = new Districting();
        BeanUtils.copyProperties(dto, districting);

        districting.setId(dto.getDistrictingId());
        districting.setJob(
            jobService.getJob(dto.getJobId())
        );
        //districting.setDistricts();
        return districting;
    }

    public District createDistrict(DistrictDto dto) {
        District district = new District();
        BeanUtils.copyProperties(dto, district);

        district.setId(dto.getDistrictId());
        district.setPrecincts(
            dto.getPrecincts().stream()
                .map(id -> dataService.getPrecinct(id))
                .collect(Collectors.toSet())
        );
        //district.setDistricting();
        return district;
    }
}
