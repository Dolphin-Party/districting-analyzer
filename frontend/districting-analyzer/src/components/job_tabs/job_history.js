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


export default class JobHistoryTab extends Component<{}, State> {
  constructor(props) {
      super(props);
      this.state = {
        jobs:this.props.jobs,
        noJobsText: this.props.noJobsText
      };
      this.handleLoad = this.handleLoad.bind(this)
      this.jobsAvailability = this.jobsAvailability.bind(this)
  }


  componentDidMount() {
   window.addEventListener('load', this.handleLoad);
}

  componentWillUnmount() {
    window.removeEventListener('load', this.handleLoad)
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
    this.props.onCancelDelete(jobId);
  }

  handleJobDataPlot=(e,key,job)=>{
    if (job.boxWhiskerAvailability.opacity == '1'){
      console.log("Job #", job, " has been selected for box whisker");
      var data= {
        jobId: key,
        status: job.status,
        state: job.state,
        randDist: job.randDist,
        comp: job.comp,
        dem: job.dem,
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
      this.props.requestJobData(data);
    }
  }

  render() {
    const cancelButton= document.getElementsByClassName('cancelButton');
    return (
      <div className='jobs-list'>
        <p className='noJobsText' style={this.props.noJobsText}>You currently have no jobs.</p>
        {Object.entries(this.state.jobs).map( ([key, job]) => (
          <div key={key} className='job-info'>
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Job #</th>
                  <th >{key}</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>State:</td>
                  <td>{job.state}</td>
                </tr>
                <tr>
                  <td>Status</td>
                  <td>{job.status}</td>
                </tr>
                <tr>
                  <td>Number of Districtings:</td>
                  <td>{job.randDist}</td>
                </tr>
                <tr>
                  <td>% of Compactness:</td>
                  <td> {job.comp}</td>
                </tr>
                <tr>
                  <td>Racial/Ethnic Demographic:</td>
                  <td> {job.dem}</td>
                </tr>
                <tr>
                  <td>% of voting age population:</td>
                  <td> {job.pvap}</td>
                </tr>
                <tr>
                  <td><Button variant="button" className="button" onClick={(e) => this.handleJobCancelDelete(e,key)}>{job.buttonOption}</Button>{' '}</td>
                  <td> <Button variant="button" className="button" style={job.boxWhiskerAvailability} onClick={(e) => this.handleJobDataPlot(e,key,job)}>Display Plot</Button>{' '}</td>
                </tr>
              </tbody>
            </Table>
          </div>
        ))}
      </div>
    )
  }
}
