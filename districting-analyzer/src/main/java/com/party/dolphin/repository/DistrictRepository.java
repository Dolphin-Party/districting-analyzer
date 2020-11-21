package com.party.dolphin.repository;

import java.util.List;

import com.party.dolphin.model.District;

import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends CrudRepository<District, Integer> {

    District findById(int id);

    List<District> findAllByDistrictingId(int districtingId);
}
