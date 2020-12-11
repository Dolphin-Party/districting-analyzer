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
import Table from 'react-bootstrap/Table'
import axios from 'axios';

const districtingMenu= document.getElementsByClassName('districtingMenu');
export default class JobHistoryTab extends Component<{}, State> {
  constructor(props) {
      super(props);
      this.state = {
        jobs:[],
        noJobsText: this.props.noJobsText,
        isLoading: true,
        stateDict: {1: "Arkansas", 2: "North Carolina", 3: "Virginia"}
      };
      this.handleLoad = this.handleLoad.bind(this)
      this.jobsAvailability = this.jobsAvailability.bind(this)
      this.jobButtonOptions = this.jobButtonOptions.bind(this)
      this.componentDidMount = this.componentDidMount.bind(this)
  }


  componentDidMount() {
   // axios.get("/backend/job/all")
   // .then(response => {
   //   this.setState({ jobs: response.data})
   //   this.setState({ jobs: [{jobId: '1', stateId:'2', status: 'Finished'}]})
   //   this.setState({ isLoading: false });
   // })
   // .then(this.jobButtonOptions())
   // .then(window.addEventListener('load', this.handleLoad))
   this.setState({ jobs: [{jobId: '1', stateId:'2', status: 'Finished'}]})
   this.setState({ isLoading: false });
}

jobButtonOptions() {
  var jobsList = this.state.jobs
  jobsList.forEach(job => {
    if(job.status != 'stopped'){
      job['buttonOption'] = 'Cancel'
    }else{
      job['buttonOption'] = 'Delete'
    }
  })
  this.setState({ jobs: jobsList})
}

  componentWillUnmount() {
    window.removeEventListener('load', this.handleLoad)
  }

  shouldComponentUpdate(){
    console.log("Component has updated!")
    return true
  }

  handleLoad = () => {
    this.jobsAvailability();
  }

  jobsAvailability(){
      if(this.props.jobs.length == 0){
        this.setState({noJobsText:{visibility: 'visible'}});
      }else{
        this.setState({noJobsText:{visibility: 'hidden'}});
      }
  }

  handleJobCancelDelete(e,jobId){
    // this.setState({ isLoading:true })
    this.props.onCancelDelete(jobId)
    .then(response => axios.get("/backend/job/all"))
    .then(response => {
      this.setState({ jobs: response.data})
    }).then(this.setState({ isLoading: false }))
  }

  handleJobSelect=(e,key,job)=>{
    if (job.status == 'Finished'){
      this.props.requestJobData(job);
    }
  }

  render() {
    const cancelButton= document.getElementsByClassName('cancelButton');
    const { isLoading, stateData } = this.state;
    if (isLoading) {
      console.log(this.state.jobs)
      return <div>Loading...</div>;
    }else{
    return (
      <div className='jobs-list'>
        <p className='noJobsText' style={this.props.noJobsText}>You currently have no jobs.</p>
        {this.state.jobs.map((job, key) => (
          <div
            key={key}
            className='job-info'>
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Job #</th>
                  <th>{job.jobId}</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>State:</td>
                  <td>{this.state.stateDict[job.stateId]}</td>
                </tr>
                <tr>
                  <td>Status</td>
                  <td>{job.status}</td>
                </tr>
                <tr>
                  <td>Number of Districtings:</td>
                  <td>{job.numberDistrictings}</td>
                </tr>
                <tr>
                  <td>% of Compactness:</td>
                  <td> {job.compactnessAmount}</td>
                </tr>
                <tr>
                  <td>Racial/Ethnic Demographic:</td>
                  <td> {job.targetDemographic}</td>
                </tr>
                <tr>
                  <td>% of voting age population:</td>
                  <td> {job.buttonOption}</td>
                </tr>
                <tr>
                  <td><Button variant="button" className="button" onClick={(e) => this.handleJobCancelDelete(e,job)}>{(job.status)=='running' ? 'Cancel' : 'Delete' }</Button>{' '}</td>
                  <td> <Button variant="button" className="button" style={job.boxWhiskerAvailability} onClick={(e) => this.handleJobSelect(e,key,job)}>Display Districting</Button>{' '}</td>
                </tr>
              </tbody>
            </Table>
          </div>
        ))}
      </div>
    )
    }
  }
}
