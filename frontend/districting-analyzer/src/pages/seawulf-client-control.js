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
    axios.post("/backend/job/init", {
      stateId: newJob.stateId,
      numberDistrictings: newJob.numberDistrictings,
      compactnessAmount: newJob.compactnessAmount,
      targetDemographic: newJob.targetDemographic,
      percentDiff: newJob.pvap,
    }).then(function(response){
      if(!response){
        console.log("Error:", response);
      }else{
        jobDict[response.data] = newJob;
        console.log("Successful!", response.data);
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
        { label: "1",  y: [1790, 2560, 3000, 4180, 2740] },
        { label: "2",  y: [2520, 3460, 4090, 4370, 3740] },
        { label: "3",  y: [2360, 2810, 3360, 4280, 3130] },
        { label: "4",  y: [3400, 3820, 4300, 4520, 4170] },
      ],
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
