package com.party.dolphin.model.enums;

public enum DemographicType {
    whiteNonHispanic("White Non-Hispanic"), hispanic("Hispanic"), black("Black"), asian("Asian"),
    americanIndian("American Indian"), pacific("Pacific"), twoOrMoreRaces("Two or More Races");

    private final String label;
    private DemographicType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}