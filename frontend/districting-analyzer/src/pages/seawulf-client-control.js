import React, { Component, useState } from 'react'
import { Map, TileLayer, GeoJSON } from 'react-leaflet'
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown'
import RangeSlider from 'react-bootstrap-range-slider';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';
import Input from '@material-ui/core/Input';

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
    // noJobsText[0].style.visibility = 'hidden';
    console.log('newJob ', newJob)
  }

  onJobCancelDelete= (jobId) => {
    var jobDict = this.state.jobs;
    // Send REST data to backend, wait for confirmation, and then:
    if (jobDict[jobId].buttonOption == 'Delete'){ //Delete
      delete jobDict[jobId];
      if( jobDict.length == 0){
        // noJobsText[0].style.visibility = 'visible';
      }
      // Delete from backend
    }else{ //Cancel & give button option as 'delete'
      jobDict[jobId].status = 'Cancelled';
      jobDict[jobId].buttonOption = 'Delete';
      console.log(jobDict)
    }
    this.setState({jobs: jobDict});
    console.log(this.state.jobs)
  }

  onJobUpdate = (jobId) => {
    // Get updates from seawulf and show on the job right away
    // Make an alert that a job is done?
    var jobDict = this.state.jobs;
    var updatedStatus;
    jobDict[jobId].status = updatedStatus;
    if (updatedStatus == 'Finished'){
      jobDict[jobId].buttonOption = 'Delete';
    }
  }

  render() {
    return (
      <div>
      <div className='seawulf-client-control'>
        <Tabs>
          <div label="Request Job">
            <RequestJobTab state={this.props.currState} dem={this.props.currDem} data={this.state.jobs} onJobAdd={this.onJobAdded}></RequestJobTab>
          </div>
          <div label="Job History">
            <JobHistoryTab jobs={this.state.jobs} onCancelDelete={this.onJobCancelDelete}></JobHistoryTab>
          </div>
        </Tabs>
      </div>
        </div>
      )
    }
  }
