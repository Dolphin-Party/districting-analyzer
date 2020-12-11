package com.party.dolphin.repository;

import java.util.List;

import com.party.dolphin.model.Precinct;

import org.springframework.data.repository.Repository;

public interface PrecinctRepository extends Repository<Precinct, String> {
    
    Precinct findById(String id);

    List<Precinct> findAllByCountyId(int countyId);
}
