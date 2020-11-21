package com.party.dolphin.repository;

import com.party.dolphin.model.Districting;

import org.springframework.data.repository.CrudRepository;

public interface DistrictingRepository extends CrudRepository<Districting, Integer> {

    Districting findById(int id);
}
