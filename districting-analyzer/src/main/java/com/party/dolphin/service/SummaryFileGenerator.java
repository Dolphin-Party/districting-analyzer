package com.party.dolphin.service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.party.dolphin.dto.FeatureCollectionDesc;
import com.party.dolphin.model.*;

import org.geojson.*;
import org.springframework.stereotype.Service;

@Service
public class SummaryFileGenerator {

    public boolean generateSummaryFile(Job job, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.err.println("Summary file already exists");
            return true;
        }

        FeatureCollectionDesc collection = new FeatureCollectionDesc();
        List<Feature> precinctFeatures =
            job.getState().getCounties().stream()
                .map(c -> c.getPrecincts())
                .flatMap(precincts -> precincts.stream())
                .map(p -> precinctToFeature(p))
                .collect(Collectors.toList());
        collection.setDescription(job.getState().getName() + " State Precincts");
        collection.setFeatures(precinctFeatures);

        List<Map<String, Object>> districtings =
            job.getDistrictings().stream()
                .map(d -> districtingToMap(d))
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stateName", job.getState().getName());
        map.put("stateID", job.getState().getAbbreviation());
        map.put("precinctGeoJson", collection);
        map.put("averageDistricting", job.getAverageDistricting());
        map.put("extremeDistricting", job.getExtremeDistricting());
        map.put("districtings", districtings);

        if (!writeJsonFile(file, map)) {
            System.err.printf("Error generating summary file for job %d\n", job.getId());
            return false;
        } else {
            return true;
        }
    }

    private boolean writeJsonFile(File file, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            file.createNewFile();
            //PrintWriter writer = new PrintWriter(new FileWriter(file));
            mapper.writeValue(file, object);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
            file.delete();
            return false;
        }
        return true;
    }

    private Map<String, Object> districtingToMap(Districting districting) {
        Map<String, Object> constraints = new HashMap<>();
        constraints.put("compactnessLimit", districting.getJob().getCompactnessAmount());
        constraints.put("populationDifferenceLimit", districting.getJob().getPercentDiff());
        constraints.put("minorityGroup", districting.getJob().getTargetDemographic());

        FeatureCollectionDesc districts = new FeatureCollectionDesc();
        List<Feature> districtFeatures =
            districting.getDistricts().stream()
                .map(d -> districtToFeature(d))
                .collect(Collectors.toList());
        districts.setDescription("Congressional Districts");
        districts.setFeatures(districtFeatures);

        Map<String, Object> map = new HashMap<>();
        map.put("districtingID", districting.getId());
        map.put("constraints", constraints);
        map.put("congressionalDistrictsGeoJSON", districts);
        return map;
    }

    private Feature districtToFeature(District district) {
        Feature feature = new Feature();
        int population = 0;
        int minorityPopulation = 0;
        int precinctMinorityPop;
        List<Map<String, String>> precinctsInfo = new ArrayList<>();

        for (Precinct p : district.getPrecincts()) {
            population += p.getPopulation();
            precinctMinorityPop = p.getVAP(district.getTargetDemographic());
            minorityPopulation += precinctMinorityPop;
            HashMap<String, String> map = new HashMap<>();
            map.put("precinctID", p.getId());
            map.put("minorityPopulation", Integer.toString(minorityPopulation));
            //map.put("minorityVotingAgePopulation", null);
            precinctsInfo.add(map);
        }

        //feature.setProperty("district", null);
        feature.setProperty("districtID", district.getId());
        feature.setProperty("population", population);
        feature.setProperty("minorityPopulation", minorityPopulation);
        //feature.setProperty("votingAgePopulation", null);
        //feature.setProperty("minorityVotingAgePopulation", null);
        //feature.setProperty("adjacentDistrict", null);
        feature.setProperty("differentCounties", district.getNumberCounties());
        feature.setProperty("precinctsInfo", precinctsInfo);
        return feature;
    }

    private Feature precinctToFeature(Precinct precinct) {
        Feature feature = new Feature();
        GeoJsonObject geometry;
        try {
            FeatureCollection featureCollection =
                new ObjectMapper().readValue(precinct.getShape(), FeatureCollection.class);
            geometry = featureCollection.getFeatures().get(0).getGeometry();
        } catch (JsonMappingException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (JsonProcessingException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        if (geometry instanceof Polygon) {
            feature.setGeometry((Polygon) geometry);
        } else if (geometry instanceof MultiPolygon) {
            feature.setGeometry((MultiPolygon) geometry);
        } else if (geometry instanceof Geometry) {
            System.err.printf("Precinct %s has geometry type\n", precinct.getId());
            feature.setGeometry((Geometry) geometry);
        }

        List<String> neighborIds =
            precinct.getNeighbors().stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        //feature.setProperty("precinct", null);
        feature.setProperty("precinctID", precinct.getId());
        feature.setProperty("county", precinct.getCounty().getName());
        feature.setProperty("countyID", precinct.getCounty().getId());
        feature.setProperty("population", precinct.getPopulation());
        //feature.setProperty("votingAgePopulation", null);
        feature.setProperty("adjacentPrecincts", neighborIds);
        return feature;
    }

}
