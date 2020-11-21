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
    };
  }
    handleStateSelect = (state) => {
      this.setState({currState:state})
      console.log('state ', this.state.currState, state, ' dem ', this.state.currDem)
      if(state != null && this.state.currDem != null){
        console.log('not null', state)
        this.setState({submitAvailability: {opacity: '1'}})
        this.setState({warnText: ''})
      }else{
        console.log('null', state)
        this.setState({submitAvailability: {opacity: '0.2'}})
        if(state == null && this.state.currDem == null){
          this.setState({warnText: 'Please select State & Demographic'})
        }else if(state == null){
          this.setState({warnText: 'Please select State'})
        }else{
          this.setState({warnText: 'Please select Demographic'})
        }
      }
      console.log('submitAvailability: ', this.state.submitAvailability)
    }

    handleDemSelect= (dem) => {
      this.setState({currDem:dem});
      console.log('state ', this.state.currState, ' dem ', this.state.currDem)
      if(dem != null && this.state.currState != null){
        this.setState({submitAvailability: {opacity: '1'}})
        this.setState({warnText: ''})
      }else{
        this.setState({submitAvailability: {opacity: '0.2'}})
        if(dem== null && this.state.currState == null){
          this.setState({warnText: 'Please select State & Demographic'})
        }else if(dem == null){
          this.setState({warnText: 'Please select State'})
        }else{
          this.setState({warnText: 'Please select Demographic'})
        }
      }
      console.log('submitAvailability: ', this.state.submitAvailability)
    }

  render() {
    return (
      <div>
      <LeafletMap currState={this.state.currState} currDem={this.state.currDem} onStateSelect={this.handleStateSelect} onDemSelect={this.handleDemSelect}/>
      <SeawulfClientControl currState={this.state.currState} currDem={this.state.currDem} submitAvailability={this.state.submitAvailability} warnText={this.state.warnText}/>
      <DataControl/>
      </div>
    )
  }
}
