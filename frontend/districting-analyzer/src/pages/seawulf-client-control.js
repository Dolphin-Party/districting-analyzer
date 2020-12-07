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
      jobIdDict: {"Arkansas":1, "North Carolina":2, "Virginia":3},
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
    }).then(function(response){
      if(!response){
        console.log("Error:", response);
      }else{
        jobDict[response.data] = newJob;
        console.log("Successful!", response.data);
      }
    })
  }

  onJobCancelDelete= (job) => {
    // var jobDict = this.state.jobs;
    if (job.status == 'stopped'){
      // delete jobDict[jobId];
      axios.post("backend/job/"+job.jobId.toString()+"/delete", {
      }).then(function(response){
        if(!response){
          console.log("Error:", response);
        }else{
          console.log("Deleted Successfully")
        }
      })
    }else{
      axios.post("backend/job/"+job.jobId.toString()+"/cancel", {
      }).then(function(response){
        if(response){
          job.status = 'stopped';
        }else{
          job.status = 'Error';
        }
      })
      // job.status = 'stopped'; //to be deleted
      // jobDict[jobId].buttonOption = 'Delete';
    }
    // this.setState({jobs: jobDict});
    if(Object.keys(this.state.jobs).length == 0){
      this.setState({noJobsText:{visibility: 'visible'}});
    }else{
      this.setState({noJobsText:{visibility: 'hidden'}});
    }

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
    if (updatedStatus == 'Finished'){
      jobDict[jobId].buttonOption = 'Delete';
      jobDict[jobId].boxWhiskerAvailability = {opacity: '1'}
    }
  }

  onJobDataRequest = (jobId) => {
    var jobPlotData = null;
    // axios.get("/job/boxWhiskerData/"{jobId}).then(
    //       result => {
    //         jobPlotData  = result.data,
    //       },
    //       error => {
    //         console.log(error)
    //       }
    //       );
    var job = this.state.jobDict[jobId]
    jobPlotData = { //temporary dummy data,to be deleted
      jobId: jobId,
      status: job.status,
      state: job.state,
      numberDistrictings: job.numberDistrictings,
      compactnessAmount: job.compactnessAmount,
      targetDemographic: job.targetDemographic,
      pvap: job.pvap,
      dataPoints: [
        { label: "1",  y: [179, 256, 300, 418, 274] },
        { label: "2",  y: [252, 346, 409, 437, 374.5] },
        { label: "3",  y: [236, 281.5, 336.5, 428, 313] },
        { label: "4",  y: [340, 382, 430, 452, 417] },
        { label: "5",  y: [194, 224.5, 342, 384, 251] },
        { label: "6",  y: [241, 255, 276.5, 294, 274.5] },
        { label: "7",  y: [340, 382, 430, 452, 417] },
        { label: "8",  y: [194, 224.5, 342, 384, 251] },
        { label: "9",  y: [241, 255, 276.5, 294, 274.5] },
        { label: "10",  y: [241, 255, 276.5, 294, 274.5] },
        { label: "11",  y: [241, 255, 276.5, 294, 274.5] },
        { label: "12",  y: [241, 255, 276.5, 294, 274.5] },
      ],
    }
    this.props.onBoxWhiskerSelect(jobPlotData);
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
