package com.party.dolphin.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrecinctDemographicPK implements Serializable {
    /* Fields */
    private static final long serialVersionUID = 1L;
    private String precinctId;
    private String demographic;

    /* Properties */
    @Column(name="precinctID", nullable=false, insertable=false, updatable=false)
    public String getPrecinctId() {
        return this.precinctId;
    }
    public void setPrecinctId(String precinctId) {
        this.precinctId = precinctId;
    }

    @Column(name="demographic", nullable=false, insertable=false, updatable=false)
    public String getDemographic() {
        return this.demographic;
    }
    public void setDemographic(String demographic) {
        this.demographic = demographic;
    }

    /* Other Methods */
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass())
            return false;

        PrecinctDemographicPK id = (PrecinctDemographicPK) o;
        return this.precinctId.equals(id.getPrecinctId()) &&
                this.demographic.equals(id.getDemographic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(precinctId, demographic);
    }
}
