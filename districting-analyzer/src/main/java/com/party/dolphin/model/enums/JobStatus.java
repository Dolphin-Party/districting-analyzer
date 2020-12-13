package com.party.dolphin.model.enums;

public enum JobStatus {
    notStarted("Not Started"), running("Running"), stopped("Stopped"),
    error("Error"), finishDistricting("Finished Districting"), finishProcessing("Finished Processing");

    private final String label;
    private JobStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}