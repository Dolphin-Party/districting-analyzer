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

import LeafletMap from './leaflet_map'
import MapViewFilter from './map-view-filter'
import SeawulfClientControl from './seawulf-client-control'
import DataControl from './data-control'


export default class Home extends Component<{}, State> {
  constructor(props) {
    super(props);
    this.state = {
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
      console.log("in home ", data)
      this.setState({boxWhiskerJob: data});
      console.log(this.state.boxWhiskerJob)
    }

  render() {
    return (
      <div>
      <LeafletMap currState={this.state.currState} currDem={this.state.currDem} onStateSelect={this.handleStateSelect} onDemSelect={this.handleDemSelect} onReset={this.handleReset}/>
      <SeawulfClientControl currState={this.state.currState} currDem={this.state.currDem} submitAvailability={this.state.submitAvailability} warnText={this.state.warnText} onBoxWhiskerSelect={this.handleBoxWhiskerSelect}/>
      <DataControl boxWhiskerData={this.state.boxWhiskerJob}/>
      </div>
    )
  }
}
