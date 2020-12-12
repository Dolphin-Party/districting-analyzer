package com.party.dolphin.model;

import com.party.dolphin.model.enums.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
@Table(name="Jobs")
public class Job {
    /* Fields */
    private int id;
    private JobStatus status;
    private State state;
    private int numberDistrictings;
    private String compactnessAmount; // TODO: annotations for enum & enum itself
    private DemographicType targetDemographic;
    private boolean isSeawulf;
    private List<Districting> districtings;
    private List<BoxWhisker> boxWhiskers;
    private int averageDistricting;
    private int extremeDistricting;

    /* Constructor */
    public Job() { }
    
    /* Properties */
    @Id
    @GeneratedValue(generator="jobIdSequence", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(
        name="jobIdSequence",
        sequenceName="JobIdSequence",
        allocationSize=5
    )
    @Column(name="ID", updatable=false)
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    public JobStatus getStatus() {
        return this.status;
    }
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stateID")
    public State getState() {
        return this.state;
    }
    public void setState(State state) {
        this.state = state;
    }

    @Column(name="numberDistrictings")
    public int getNumberDistrictings() {
        return this.numberDistrictings;
    }
    public void setNumberDistrictings(int numberDistrictings) {
        this.numberDistrictings = numberDistrictings;
    }

    @Column(name="compactnessAmount")
    public String getCompactnessAmount() {
        return this.compactnessAmount;
    }
    public void setCompactnessAmount(String compactnessAmount) {
        this.compactnessAmount = compactnessAmount;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="targetDemographic")
    public DemographicType getTargetDemographic() {
        return this.targetDemographic;
    }
    public void setTargetDemographic(DemographicType targetDemographic) {
        this.targetDemographic = targetDemographic;
    }

    @Column(name="isSeawulf")
    public boolean getIsSeawulf() {
        return this.isSeawulf;
    }
    public void setIsSeawulf(boolean isSeawulf) {
        this.isSeawulf = isSeawulf;
    }

    @OneToMany(mappedBy="job")
    public List<Districting> getDistrictings() {
        return this.districtings;
    }
    public void setDistrictings(List<Districting> districtings) {
        this.districtings = districtings;
    }

    @OneToMany(mappedBy="job")
    public List<BoxWhisker> getBoxWhiskers() {
        return this.boxWhiskers;
    }
    public void setBoxWhiskers(List<BoxWhisker> boxWhiskers) {
        this.boxWhiskers = boxWhiskers;
    }

    @Column(name="averageDistricting")
    public int getAverageDistricting() {
        return this.averageDistricting;
    }
    public void setAverageDistricting(int averageDistricting) {
        this.averageDistricting = averageDistricting;
    }

    @Column(name="extremeDistricting")
    public int getExtremeDistricting() {
        return this.extremeDistricting;
    }
    public void setExtremeDistricting(int extremeDistricting) {
        this.extremeDistricting = extremeDistricting;
    }    

    /* Server Processing */
    // TODO: Test these methods
    public void analyzeJobResults() {
        this.calcNumberCounties();
        this.genOrderedDistricts();
        this.genBoxWhisker();
        this.findRepresentativeDistrictings();
    }

    private void calcNumberCounties() {
        for (Districting d : this.districtings) {
            d.calcNumberCounties();
        }
    }

    private void genOrderedDistricts() {
        for (Districting d : this.districtings) {
            d.genOrderedDistricts(this.targetDemographic);
        }
    }

    private void genBoxWhisker() {
        int numDistricts = this.districtings.get(0).getDistricts().size();
        int numDistrictings = this.districtings.size();
        int i, j;
        List<Double> boxWhiskerData = new ArrayList<Double>(numDistricts);
        List<BoxWhisker> boxWhiskerPlots = new ArrayList<BoxWhisker>(numDistricts);
        
        for (i = 0; i < numDistricts; i++) {
            for (j = 0; j < numDistrictings; j++) {
                boxWhiskerData.add(
                    this.districtings.get(j)
                        .getDistricts().get(i)
                        .getTargetDemographicPercentVap()
                );
            }

            BoxWhisker plot = new BoxWhisker();
            plot.setJob(this);
            plot.setOrder(i);
            plot.calcStats(boxWhiskerData);

            boxWhiskerPlots.add(plot);
            boxWhiskerData.clear();
        }

        this.boxWhiskers = boxWhiskerPlots;
    }

    // Based on euclidean distance for target demographic percent VAP
    // compared to the average for each district
    private void findRepresentativeDistrictings() {
        List<Double> districtAverages = this.boxWhiskers.stream()
            .map(bw -> bw.getAverage())
            .collect(Collectors.toList());
        Double euclidDist;
        Double minDist = Double.POSITIVE_INFINITY;
        Double maxDist = Double.NEGATIVE_INFINITY;
        Districting avgD = this.districtings.get(0);
        Districting extremeD = this.districtings.get(0);

        for (Districting d : this.districtings) {
            Iterator<Double> averageIt = districtAverages.iterator();
            euclidDist = 0.0;

            for (District di : d.getDistricts()) {
                euclidDist += Math.pow(di.getTargetDemographicPercentVap() - averageIt.next(), 2);
            }

            if (euclidDist < minDist) {
                minDist = euclidDist;
                avgD = d;
            }
            if (euclidDist > maxDist) {
                maxDist = euclidDist;
                extremeD = d;
            }
        }

        this.averageDistricting = avgD.getId();
        this.extremeDistricting = extremeD.getId();
    }

}