package com.party.dolphin.service;

import com.party.dolphin.model.*;

import net.bytebuddy.asm.Advice.This;

import com.party.dolphin.dto.*;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

public class DatabaseService {
    /* Fields */
    private static DatabaseService singleton = new DatabaseService();
    
    /* Constructor */
    private DatabaseService() {};

    /* Get the singleton instance */
    public static DatabaseService getInstance() {
        return singleton;
    }

    /* Other Methods */
    public static State findStateById(int id) {
        return new State();
    }

    public static State findStateByName(String name) {
        return new State();
    }

    public static County findCountyById(int id) {
        return new County();
    }

    public static Precinct findPrecinctById(int id) {
        return new Precinct();
    }

    public static Job findJobById(String uuid) {
        return new Job();
    }

    public static List<Job> findAllJobs(int stateId) {
        return new ArrayList<Job>();
    }

    public static Districting findDistrictingById(String uuid) {
        return new Districting();
    }

    public static District findDistrictById(String uuid) {
        return new District();
    }

    public static Set<Precinct> findPrecinctGraph(int stateId) {
        return new HashSet<Precinct>();
    }
}