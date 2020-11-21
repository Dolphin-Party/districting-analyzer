package com.party.dolphin.repository;

import com.party.dolphin.model.State;

import org.springframework.data.repository.Repository;

public interface StateRepository extends Repository<State, Integer> {

    State findById(int id);
    
    State findByName(String name);
}
