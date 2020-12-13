package com.party.dolphin.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PrecinctNeighborPK implements Serializable {
    /* Fields */
    private static final long serialVersionUID = 1L;
    private String precinctId;
    private String neighborId;

    /* Constructors */
    public PrecinctNeighborPK() {
    }

    public PrecinctNeighborPK(String precinctId, String neighborId) {
        this.precinctId = precinctId;
        this.neighborId = neighborId;
    }

    /* Properties */
    public String getPrecinctId() {
        return this.precinctId;
    }
    public void setPrecinctId(String precinctId) {
        this.precinctId = precinctId;
    }

    public String getNeighborId() {
        return this.neighborId;
    }
    public void setNeighborId(String neighborId) {
        this.neighborId = neighborId;
    }

    /* Other Methods */
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass())
            return false;

        PrecinctNeighborPK id = (PrecinctNeighborPK) o;
        return this.precinctId.equals(id.precinctId) &&
                this.neighborId.equals(id.neighborId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(precinctId, neighborId);
    }
}
