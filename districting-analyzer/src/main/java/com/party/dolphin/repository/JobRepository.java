package com.party.dolphin.repository;

import java.util.List;

import com.party.dolphin.model.Job;

import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Integer> {
    
    Job findById(int id);

    List<Job> findAllByStateId(int stateId);

    List<Job> findAll();
}
