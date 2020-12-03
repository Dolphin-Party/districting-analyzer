package com.party.dolphin.dto;

public class BoxWhiskerDto {
    /* Fields */
    private int order;
    private double average;
    private double min;
    private double q1;
    private double median;
    private double q3;
    private double max;

    /* Properties */
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    public double getAverage() {
        return this.average;
    }
    public void setAverage(double average) {
        this.average = average;
    }

    public double getMin() {
        return this.min;
    }
    public void setMin(double min) {
        this.min = min;
    }

    public double getQ1() {
        return this.q1;
    }
    public void setQ1(double q1) {
        this.q1 = q1;
    }

    public double getMedian() {
        return this.median;
    }
    public void setMedian(double median) {
        this.median = median;
    }

    public double getQ3() {
        return this.q3;
    }
    public void setQ3(double q3) {
        this.q3 = q3;
    }

    public double getMax() {
        return this.max;
    }
    public void setMax(double max) {
        this.max = max;
    }

}
