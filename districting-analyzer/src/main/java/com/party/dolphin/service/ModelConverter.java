package com.party.dolphin.service;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.model.enums.DemographicType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Check how many sets can be replace with BeanUtils.copyProperties
// Docs suggest BeanWrapper?
@Service
public class ModelConverter {

    @Autowired
    DataService dataService;

    public StateDto createStateDto(State state) {
        StateDto dto = new StateDto();
        BeanUtils.copyProperties(state, dto);

        dto.setStateId(state.getId());
        dto.setPopulation(state.getPopulation());
        dto.setCounties(
            new HashSet<Integer>(
                dataService.getCountyIdsByState(state.getId())
            )
        );
        dto.setCanonicalDistrictingId(
            state.getCanonicalDistricting().getId()
        );
        return dto;
    }

    public CountyDto createCountyDto(County county) {
        CountyDto dto = new CountyDto();
        dto.setCountyId(county.getId());
        dto.setName(county.getName());
        dto.setStateId(county.getState().getId());
        dto.setShape(county.getShape());
        dto.setPrecincts(
            new HashSet<String>(
                dataService.getPrecinctIdsByCounty(county.getId())
            )
        );
        return dto;
    }

    public PrecinctDto createPrecinctDto(Precinct precinct) {
        PrecinctDto dto = new PrecinctDto();
        BeanUtils.copyProperties(precinct, dto);

        dto.setPrecinctId(precinct.getId());
        dto.setCountyId(precinct.getCounty().getId());
        dto.setNeighbors(
            precinct.getNeighbors().stream()
                .map(p -> p.getId())
                .collect(Collectors.toSet())
        );

        Map<DemographicType, Integer> map =
            new EnumMap<DemographicType, Integer>(DemographicType.class);
        precinct.getDemographics().stream()
            .forEach(d -> map.put(d.getDemographic(), d.getPopulation()));
        dto.setDemographics(map);
        return dto;
    }

    public JobDto createJobDto(Job job) {
        JobDto dto = new JobDto();
        BeanUtils.copyProperties(job, dto);

        dto.setJobId(job.getId());
        dto.setStateId(job.getState().getId());
        if (job.getDistrictings() != null) {
            dto.setDistrictings(
                job.getDistrictings().stream()
                    .map(d -> d.getId())
                    .collect(Collectors.toList())
            );
        }
        if (job.getBoxWhiskers() != null) {
            dto.setBoxWhiskers(
                job.getBoxWhiskers().stream()
                    .map(bw -> createBoxWhiskerDto(bw))
                    .collect(Collectors.toList())
            );
        }

        return dto;
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
                .map(di -> di.getId())
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
                .map(p -> p.getId())
                .collect(Collectors.toSet())
        );
        return dto;
    }
}
