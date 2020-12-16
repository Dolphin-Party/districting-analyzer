package com.party.dolphin.dto;

import org.geojson.FeatureCollection;

public class FeatureCollectionDesc extends FeatureCollection {
    /* Field */
    private String description;

    /* Property */
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
