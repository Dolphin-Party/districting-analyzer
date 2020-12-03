package com.party.dolphin.service;

import java.util.stream.Collectors;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

// TODO: Check how many sets can be replace with BeanUtils.copyProperties
// Docs suggest BeanWrapper?
@Service
public class ModelConverter {
    public StateDto createStateDto(State state) {
        StateDto stateDto = new StateDto();
        stateDto.setStateId(state.getId());
        stateDto.setName(state.getName());
        stateDto.setShape(state.getShape());
        stateDto.setPopulation(state.getPopulation());
        stateDto.setCounties(
            state.getCounties().stream()
                .mapToInt(c -> c.getId())
                .boxed()
                .collect(Collectors.toSet())
        );
        stateDto.setCanonicalDistrictingId(
            state.getCanonicalDistricting().getId()
        );
        return stateDto;
    }

    public CountyDto createCountyDto(County county) {
        CountyDto countyDto = new CountyDto();
        countyDto.setCountyId(county.getId());
        countyDto.setName(county.getName());
        countyDto.setStateId(county.getState().getId());
        countyDto.setShape(county.getShape());
        countyDto.setPrecincts(
            county.getPrecincts().stream()
                .mapToInt(p -> p.getId())
                .boxed()
                .collect(Collectors.toSet())
        );
        return countyDto;
    }

    public PrecinctDto createPrecinctDto(Precinct precinct) {
        PrecinctDto precinctDto = new PrecinctDto();
        BeanUtils.copyProperties(precinct, precinctDto);

        precinctDto.setPrecinctId(precinct.getId());
        precinctDto.setCountyId(precinct.getCounty().getId());
        precinctDto.setNeighbors(
            precinct.getNeighbors().stream()
                .mapToInt(p -> p.getId())
                .boxed()
                .collect(Collectors.toSet())
        );
        return precinctDto;
    }

    public JobDto createJobDto(Job job) {
        JobDto jobDto = new JobDto();
        BeanUtils.copyProperties(job, jobDto);

        jobDto.setStateId(job.getState().getId());
        jobDto.setDistrictings(
            job.getDistrictings().stream()
                .mapToInt(d -> d.getId())
                .boxed()
                .collect(Collectors.toList())
        );
        jobDto.setBoxWhiskers(
            job.getBoxWhiskers().stream()
                .map(bw -> createBoxWhiskerDto(bw))
                .collect(Collectors.toList())
        );

        return jobDto;
    }

    public BoxWhiskerDto createBoxWhiskerDto(BoxWhisker boxWhisker) {
        BoxWhiskerDto dto = new BoxWhiskerDto();
        BeanUtils.copyProperties(boxWhisker, dto);
        return dto;
    }

    public DistrictingDto createDistrictingDto(Districting districting) {
        DistrictingDto dto = new DistrictingDto();
        BeanUtils.copyProperties(districting, dto);

        dto.setDistrictingId(districting.getId());
        dto.setJobId(districting.getJob().getId());
        dto.setDistricts(
            districting.getDistricts().stream()
                .mapToInt(di -> di.getId())
                .boxed()
                .collect(Collectors.toList())
        );
        return dto;
    }

    public DistrictDto createDistrictDto(District district) {
        DistrictDto dto = new DistrictDto();
        BeanUtils.copyProperties(district, dto);

        dto.setDistrictId(district.getId());
        dto.setDistrictingId(district.getDistricting().getId());
        dto.setPrecincts(
            district.getPrecincts().stream()
                .mapToInt(p -> p.getId())
                .boxed()
                .collect(Collectors.toSet())
        );
        return dto;
    }
}
