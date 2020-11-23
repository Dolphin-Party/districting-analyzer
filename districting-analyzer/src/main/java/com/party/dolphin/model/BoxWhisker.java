package com.party.dolphin.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="BoxWhiskers")
public class BoxWhisker {
    /* Fields */
    private int id;
    private Job job;
    private int order;
    private double average;
    private double min;
    private double q1;
    private double median;
    private double q3;
    private double max;

    /* Properties */
    @Id
    @GeneratedValue(generator="boxWhiskerIdSequence", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(
        name="boxWhiskerIdSequence",
        sequenceName="BoxWhiskerIdSequence",
        allocationSize=10
    )
    @Column(name="ID", updatable=false)
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="jobID")
    public Job getJob() {
        return this.job;
    }
    public void setJob(Job job) {
        this.job = job;
    }

    @Column(name="`order`")
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    @Column(name="average")
    public double getAverage() {
        return this.average;
    }
    public void setAverage(double average) {
        this.average = average;
    }

    @Column(name="min")
    public double getMin() {
        return this.min;
    }
    public void setMin(double min) {
        this.min = min;
    }

    @Column(name="q1")
    public double getQ1() {
        return this.q1;
    }
    public void setQ1(double q1) {
        this.q1 = q1;
    }

    @Column(name="median")
    public double getMedian() {
        return this.median;
    }
    public void setMedian(double median) {
        this.median = median;
    }

    @Column(name="q3")
    public double getQ3() {
        return this.q3;
    }
    public void setQ3(double q3) {
        this.q3 = q3;
    }

    @Column(name="max")
    public double getMax() {
        return this.max;
    }
    public void setMax(double max) {
        this.max = max;
    }
}
