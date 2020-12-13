package com.party.dolphin.dto;

public class PrecinctNeighborDto {
    /* Fields */
    private String precinctId;
    private String neighborId;

    /* Constructors */
    public PrecinctNeighborDto() {
    }
    public PrecinctNeighborDto(String precinctId, String neighborId) {
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

}
