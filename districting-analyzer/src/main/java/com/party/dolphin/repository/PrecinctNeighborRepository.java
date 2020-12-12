package com.party.dolphin.repository;

import java.util.List;

import com.party.dolphin.model.PrecinctNeighbor;
import com.party.dolphin.model.PrecinctNeighborPK;

import org.springframework.data.repository.Repository;

public interface PrecinctNeighborRepository extends Repository<PrecinctNeighbor, PrecinctNeighborPK> {

    List<PrecinctNeighbor> findAllByPrecinctId(int precinctId);
}
