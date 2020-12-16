import React, { Component, useState } from 'react'
import axios from 'axios';
import { Map, TileLayer, GeoJSON } from 'react-leaflet'
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown'
import RangeSlider from 'react-bootstrap-range-slider';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';
import Input from '@material-ui/core/Input';

import LeafletMap from './leaflet_map'
import MapViewFilter from './map-view-filter'
import SeawulfClientControl from './seawulf-client-control'
import DataControl from './data-control'

// import teamStates from '../assets/geojson/teamstates.json'
// import AK_frontend_precincts from '../assets/geojson/AK_frontend_precincts/AK_frontend_precincts.json'
// import virginiaPrecincts from '../assets/geojson/VirginiaPrecincts_.json'

const ReactDOM = require('react-dom');

export default class Home extends Component<{}, State> {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      stateData: undefined,
      currState: null,
      currDem: null,
      submitAvailability: {opacity: '0.2'},
      warnText:'Please select State & Demographic',
      districtingMenuStyle: {visibility: 'hidden'},
      districtingState: null,
      districtingOn: false,
      districtingJobId: null,
      enactedPlan: {
        "Arkansas": [
      		{ x: 0, y: 2150},
      		{ x: 1, y: 1750},
      		{ x: 2, y: 3250},
      		{ x: 3, y: 6350},
      	],
        "North Carolina": [
      		{ x: 0, y: 2150},
      		{ x: 1, y: 1750},
      		{ x: 2, y: 3250},
      		{ x: 3, y: 6350},
          { x: 4, y: 2150},
      		{ x: 5, y: 1750},
      		{ x: 6, y: 3250},
      		{ x: 7, y: 6350},
          { x: 8, y: 2150},
      		{ x: 9, y: 1750},
      		{ x: 10, y: 3250},
      		{ x: 11, y: 6350},
          { x: 12, y: 1750},
      		{ x: 13, y: 3250},
      	],
        "Virginia:":[
          { x: 0, y: 2150},
      		{ x: 1, y: 1750},
      		{ x: 2, y: 3250},
      		{ x: 3, y: 6350},
          { x: 4, y: 2150},
      		{ x: 5, y: 1750},
      		{ x: 6, y: 3250},
      		{ x: 7, y: 6350},
          { x: 8, y: 2150},
      		{ x: 9, y: 1750},
      		{ x: 10, y: 3250},
      		{ x: 11, y: 6350},
      	]
      },
      boxWhiskerJob: {
        jobId: '',
        status: '',
        state: '',
        randDist: '',
        comp: '',
        dem: '',
        pvap: '',
        dataPoints: [],
      },
    };
  }

  componentDidMount() {
    axios.get("/backend/state/all/info").then(response => {
      this.setState({ stateData: [JSON.parse(response.data[2].shape), JSON.parse(response.data[1].shape), JSON.parse(response.data[0].shape)]});
      this.setState({ isLoading: false });
    })
  }

  handleStateSelect = (state) => {
    this.setState({currState:state})
    this.checkAvailability(state, this.state.currDem)
  }

  handleDemSelect= (dem) => {
    this.setState({currDem:dem});
    this.checkAvailability(this.state.currState, dem)
  }

  handleReset=()=>{
    this.setState({submitAvailability: {opacity: '0.2'}})
    this.setState({currState:null});
    this.setState({currDem:null});
    this.setState({warnText: 'Please select State & Demographic'})
  }

  checkAvailability = (option1, option2) =>{
    if(option1 != null && option2 != null){ //if both are selected
      this.setState({submitAvailability: {opacity: '1'}})
      this.setState({warnText: ''})
    }else{ //if either/both are NOT selected
      this.setState({submitAvailability: {opacity: '0.2'}})
      if(option1 == null && option2 == null){ //if both are NOT selected
        this.setState({warnText: 'Please select State & Demographic'})
      }else if(option1 == null){ //if demographic not selected
        this.setState({warnText: 'Please select State'})
      }else{ //if state is not
        this.setState({warnText: 'Please select Demographic'})
      }
    }
  }

  handleHeatMapSelect = () => {
    this.setState({districtingMenuStyle: {visibility: 'hidden'}});
  }

  handleDistrictingClose = () => {
    this.setState({districtingMenuStyle: {visibility: 'hidden'}});
  }

  handleBoxWhiskerSelect = (data, job) => {
    this.setState({boxWhiskerJob: data});
    this.setState({districtingMenuStyle: {visibility: 'visible'}, districtingOn: true});
    this.setState({districtingState: job.stateId})
    this.setState({districtingJobId: job.jobId})
  }

  render() {
    const { isLoading, stateData } = this.state;
    if (isLoading) {
      return <div>Loading...</div>;
    }else{
      return (
        <div>
        <LeafletMap
          districtingMenuStyle={this.state.districtingMenuStyle} districtingState={this.state.districtingState} districtingOn={this.state.districtingOn} districtingJobId={this.state.districtingJobId}
          stateData={this.state.stateData} currState={this.state.currState} currDem={this.state.currDem}
          onStateSelect={this.handleStateSelect} onDemSelect={this.handleDemSelect} onReset={this.handleReset} handleHeatMapSelect={this.handleHeatMapSelect} handleDistrictingClose={this.handleDistrictingClose}/>
        <SeawulfClientControl currState={this.state.currState} currDem={this.state.currDem} submitAvailability={this.state.submitAvailability} warnText={this.state.warnText} onBoxWhiskerSelect={this.handleBoxWhiskerSelect}/>
        <DataControl
          boxWhiskerData={this.state.boxWhiskerJob}
          enactedPlan={this.state.enactedPlan[this.state.boxWhiskerJob.state]}
          />
        </div>
      )
    }
  }
}
