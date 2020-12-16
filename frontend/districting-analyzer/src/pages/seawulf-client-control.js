import React, { Component, useState } from 'react'
import { Map, TileLayer, GeoJSON } from 'react-leaflet'
import axios from 'axios';
import Button from 'react-bootstrap/Button';
import Tabs from "../components/job_tabs";
import RequestJobTab from "../components/job_tabs/request_job"
import JobHistoryTab from "../components/job_tabs/job_history"


export default class SeawulfClientControl extends Component<{}, State> {

  constructor(props) {
    super(props);
    this.state = {
      currentState: this.props.currState,
      currentDem: this.props.currDem,
      nextId: 1,
      jobs: {},
      noJobsText:{visibility: 'visible',},
      submitAvailability: this.props.submitAvailability,
      jobIdDict: {"Arkansas":5, "North Carolina":37, "Virginia":51},
      stateDict: {5: "Arkansas", 37: "North Carolina", 51: "Virginia"}
    };
    this.onJobAdded = this.onJobAdded.bind(this);
  }

  onJobAdded = (newJob) => {
    var jobDict = this.state.jobs;
    var jobIdDict = this.state.jobIdDict
    newJob.state = this.props.currState;
    newJob.targetDemographic= this.props.currDem;
    newJob.status = 'Submitted';
    newJob.stateId = jobIdDict[newJob.state]
    this.setState({nextId:this.state.nextId+1});
    this.setState({jobs: jobDict});
    this.setState({noJobsText:{visibility: 'hidden'}});
    console.log("newJob.pvap", newJob.pvap/100)
    axios.post("/backend/job/init", {
      stateId: newJob.stateId,
      numberDistrictings: newJob.numberDistrictings,
      compactnessAmount: newJob.compactnessAmount/100,
      targetDemographic: newJob.targetDemographic/100,
      percentDiff: newJob.pvap,
    }).then(function(response){
      if(!response){
        console.log("Error:", response);
      }else{
        console.log("Successful!", response.data);
        jobDict[response.data] = newJob;
        alert("Successfully Created Job #", response.data)
      }
    })
  }

  onJobCancelDelete= (job) => {
    return new Promise((resolve, reject) => {
      if (job.status == 'stopped'){
        // delete jobDict[jobId];
        axios.post("backend/job/"+job.jobId.toString()+"/delete", {
        }).then(function(response){
          if(!response){
            console.log("Job Delete Error:", response);
          }else{
            alert("Job #"+job.jobId+" Deleted Successfully")
          }
        })
      }else{
        axios.post("backend/job/"+job.jobId.toString()+"/cancel", {
        }).then(function(response){
          if(response){
            job.status = 'stopped';
            alert("Job #"+job.jobId+" Cancelled Successfully")
          }else{
            console.log("Job Delete Error:", response);
          }
        })
      }
      if(Object.keys(this.state.jobs).length == 0){
        this.setState({noJobsText:{visibility: 'visible'}});
      }else{
        this.setState({noJobsText:{visibility: 'hidden'}});
      }
      axios.get("/backend/job/all")
      .then(response => {
        console.log("Within seawulf ctr, ", response.data)
      }).then(resolve("okay"))
    });
  }

  onJobUpdate = (jobId) => {
    var jobDict = this.state.jobs;
    var updatedStatus = null;
    // axios.get("/job/update/jobId").then(
    //       result => {
    //         updatedStatus = result.data;
    //         jobDict[jobId].status = updatedStatus;
    //         this.setState({
    //           jobs: jobDict,
    //         });
    //       },
    //       error => {
    //         this.setState({
    //           error
    //         });
    //       }
    //       );
    jobDict[jobId].status = updatedStatus;
    if (updatedStatus == 'finishDistricting'){
      jobDict[jobId].buttonOption = 'Delete';
      jobDict[jobId].boxWhiskerAvailability = {opacity: '1'}
    }
  }

  onJobDataRequest = (job) => {
    var jobPlotData = null;
    // axios.get("/job/boxWhiskerData/"{jobId}).then(
    //       result => {
    //         jobPlotData  = result.data,
    //       },
    //       error => {
    //         console.log(error)
    //       }
    //       );
    jobPlotData = { //temporary dummy data,to be deleted
      jobId: job.jobId,
      status: job.status,
      state: this.state.stateDict[job.stateId],
      numberDistrictings: job.numberDistrictings,
      compactnessAmount: job.compactnessAmount,
      targetDemographic: job.targetDemographic,
      pvap: job.pvap,
      dataPoints: [
	{
		label: "1",
		y: [
		0.25817555938037868,
		3.4382812592566266,
		4.7795233789152236,
		4.7795233789152236,
		4.381995713265063
		]
	},
	{
		label: "2",
		y: [
		0.7552780255724056,
		4.776183742152878,
		10.702671323893827,
		11.64867246163355,
		6.163893932427398
		]
	},
	{
		label: "3",
		y: [
		4.226035956754788,
		11.920322057824019,
		15.53556333776114,
		17.31437598736177,
		13.067687043468462
		]
	},
	{
		label: "4",
		y: [
		6.374939054119942,
		16.973057415813309,
		18.050642045664805,
		18.493173097076535,
		17.714613810368907
		]
	},
	{
		label: "5",
		y: [
		6.90051438090221,
		17.856185503870878,
		19.934109842672515,
		20.518569642056583,
		18.062578910527727
		]
	},
	{
		label: "6",
		y: [
		17.946179876153864,
		18.480245453758898,
		22.256793289427082,
		23.8422407141306,
		20.961294223574112
		]
	},
	{
		label: "7",
		y: [
		18.44350779363581,
		18.625679878256363,
		24.349967227332653,
		25.3452394869443,
		21.998520558419934
		]
	},
	{
		label: "8",
		y: [
		20.675801605112015,
		22.112759527002956,
		26.041621514884433,
		26.041621514884433,
		23.82955897882897
		]
	},
	{
		label: "9",
		y: [
		21.92841561353693,
		24.282839308251186,
		26.07086438940391,
		26.75132211388938,
		25.792294437740915
		]
	},
	{
		label: "10",
		y: [
		24.960460317221225,
		25.79521407353609,
		26.2739945834991,
		27.603679845522144,
		26.041621514884433
		]
	},
	{
		label: "11",
		y : [
		26.041621514884433,
		26.041621514884433,
		30.387143900657415,
		32.276426535803976,
		28.077773875877554
		]
	},
	{
		label: "12",
		y: [
		29.063634149495,
		30.01377416245124,
		33.38649437252428,
		35.5936829282732,
		31.120366771492175
		]
	},
	{
		label: "13",
		y: [
		30.214294049393536,
		30.214294049393536,
		68.16555952989269,
		88.98404048267808,
		50.80953071383694
		]
	}
]
    }
    this.props.onBoxWhiskerSelect(jobPlotData, job);
  }


  render() {
    return (
      <div className='rightside'>
      <div className='seawulf-client-control'>
        <Tabs>
          <div label="Request Job">
            <RequestJobTab state={this.props.currState} dem={this.props.currDem} data={this.state.jobs} submitAvailability={this.props.submitAvailability} warnText={this.props.warnText} onJobAdd={this.onJobAdded}></RequestJobTab>
          </div>
          <div label="Job History">
            <JobHistoryTab jobs={this.state.jobs} noJobsText={this.state.noJobsText} onCancelDelete={this.onJobCancelDelete} requestJobData={this.onJobDataRequest}></JobHistoryTab>
          </div>
        </Tabs>
      </div>
        </div>
      )
    }
  }
