import React, { Component, useState } from 'react'
import { Map, TileLayer, GeoJSON } from 'react-leaflet'
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
    };
    this.onJobAdded = this.onJobAdded.bind(this);
  }

  onJobAdded = (newJob) => {
    this.setState({currentState:this.props.currState});
    this.setState({currentDem:this.props.currDem});
    var jobDict = this.state.jobs;
    newJob.state = this.props.currState;
    newJob.dem= this.props.currDem;
    newJob.status = 'Submitted';
    jobDict[this.state.nextId] = newJob;
    this.setState({nextId:this.state.nextId+1});
    this.setState({jobs: jobDict});
    this.setState({noJobsText:{visibility: 'hidden'}});
  }

  onJobCancelDelete= (jobId) => {
    var jobDict = this.state.jobs;
    // Send REST data to backend, wait for confirmation, and then:
    if (jobDict[jobId].buttonOption == 'Delete'){
      delete jobDict[jobId];
      if( jobDict.length == 0){
      }
      // Delete from backend
    }else{
      jobDict[jobId].status = 'Cancelled';
      jobDict[jobId].buttonOption = 'Delete';
    }
    this.setState({jobs: jobDict});
    if(Object.keys(this.state.jobs).length == 0){
      this.setState({noJobsText:{visibility: 'visible'}});
    }else{
      this.setState({noJobsText:{visibility: 'hidden'}});
    }

  }

  onJobUpdate = (jobId) => {
    // Get updates from seawulf and show on the job right away
    // Make an alert that a job is done?
    var jobDict = this.state.jobs;
    var updatedStatus;
    jobDict[jobId].status = updatedStatus;
    if (updatedStatus == 'Finished'){
      jobDict[jobId].buttonOption = 'Delete';
      jobDict[jobId].boxWhiskerAvailability = {opacity: '1'}
    }
  }

  onJobDataRequest = (data) => {
    // send to box whisker thru home
    this.props.onBoxWhiskerSelect(data);
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
