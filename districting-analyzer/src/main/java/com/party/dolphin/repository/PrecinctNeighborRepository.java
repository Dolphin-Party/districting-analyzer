package com.party.dolphin.repository;

import java.util.List;

import com.party.dolphin.model.PrecinctNeighbor;
import com.party.dolphin.model.PrecinctNeighborId;

import org.springframework.data.repository.Repository;

public interface PrecinctNeighborRepository extends Repository<PrecinctNeighbor, PrecinctNeighborId> {

    List<PrecinctNeighbor> findAllByPrecinctId(int precinctId);
}
