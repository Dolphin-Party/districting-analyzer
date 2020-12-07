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
    console.log("Home did mount")
    axios.get("/backend/state/all/info").then(response => {
      this.setState({ stateData: [JSON.parse(response.data[0].shape), JSON.parse(response.data[1].shape), JSON.parse(response.data[2].shape)]});
      this.setState({ isLoading: false });
    })
  }

  handleStateSelect = (state) => {
    this.setState({currState:state})
    this.checkAvailability(state, this.state.currDem)
  }

  handleDemSelect= (dem) => {
    this.setState({currDem:dem});
    this.checkAvailability(dem, this.state.currState)
  }

  handleReset=()=>{
    this.setState({submitAvailability: {opacity: '0.2'}})
    this.setState({currState:null});
    this.setState({currDem:null});
    this.setState({warnText: 'Please select State & Demographic'})
  }

  checkAvailability = (option1, option2) =>{
    if(option1 != null && option2 != null){
      this.setState({submitAvailability: {opacity: '1'}})
      this.setState({warnText: ''})
    }else{
      this.setState({submitAvailability: {opacity: '0.2'}})
      if(option1 == null && option2 == null){
        this.setState({warnText: 'Please select State & Demographic'})
      }else if(option1 == null){
        this.setState({warnText: 'Please select State'})
      }else{
        this.setState({warnText: 'Please select Demographic'})
      }
    }
  }

  handleBoxWhiskerSelect = (data) => {
    this.setState({boxWhiskerJob: data});
  }

  render() {
    const { isLoading, stateData } = this.state;
    if (isLoading) {
      return <div>Loading...</div>;
    }else{
      return (
        <div>
        <LeafletMap stateData={this.state.stateData} currState={this.state.currState} currDem={this.state.currDem} onStateSelect={this.handleStateSelect} onDemSelect={this.handleDemSelect} onReset={this.handleReset}/>
        <SeawulfClientControl currState={this.state.currState} currDem={this.state.currDem} submitAvailability={this.state.submitAvailability} warnText={this.state.warnText} onBoxWhiskerSelect={this.handleBoxWhiskerSelect}/>
        <DataControl boxWhiskerData={this.state.boxWhiskerJob}/>
        </div>
      )
    }
  }
}
