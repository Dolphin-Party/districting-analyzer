package com.party.dolphin.repository;

import java.util.List;

import com.party.dolphin.model.County;

import org.springframework.data.repository.Repository;

public interface CountyRepository extends Repository<County, Integer> {

    County findById(int id);

    <T> List<T> findAllByStateId(int stateId, Class<T> type);
}
