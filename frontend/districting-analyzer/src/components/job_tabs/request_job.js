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

import InputSlider from '../slider'
import CompactnessSlider from '../compactness_slider'

const [ value, setValue ] = useState(0);
const submitButton= document.getElementsByClassName('submitButton');
const warnText= document.getElementsByClassName('warnText');

export default class RequestJobTab extends Component<{}, State> {
  constructor(props) {
    super(props);
    this.state = {
      stateId : null,
      status: null,
      state: null,
      numberDistrictings: null,
      compactnessAmount: null,
      targetDemographic: null,
      pvap: null,
      buttonOption: 'Cancel',
      boxWhiskerAvailability: {opacity: '0.2'},
      compDict: {60: 'Least', 65:'Less', 70:'Average', 75:'Very', 80:'Extremely'}
    };
    this.handleJobAdd = this.handleJobAdd.bind(this)
  }

  handleJobAdd = () =>{
    if(this.props.submitAvailability.opacity == 1){
      this.props.onJobAdd(this.state);
    }
  }
  setNumRandomDistrictings = (newValue) => {
    this.setState({numberDistrictings: newValue});
  }
  setCompactness = (newValue) => {
    var compDict=this.state.compDict
    var temp=compDict[newValue]
    this.setState({compactnessAmount: temp});
  }
  setTargetDemographic = (newValue) => {
    this.setState({targetDemographic: newValue});
  }
  setVAP = (newValue) => {
    console.log("setVAP", newValue)
    this.setState({pvap: newValue});
  }
  render() {
    const randDistBound = {min: 0, max: 5000};
    const vapBound = {min:0, max:100}
    return (
      <div className='job-request'>
      <p className='title'>Generating Districtings</p>
      <p># of Random Districtings</p>
      <InputSlider data={randDistBound} onNewNumber={this.setNumRandomDistrictings}></InputSlider>
      <p>Compactness %</p>
      <CompactnessSlider onNewNumber={this.setCompactness}></CompactnessSlider>
      <p>% Deviation From Ideal </p>
      <InputSlider data={vapBound} onNewNumber={this.setVAP}></InputSlider>
      <Button variant="button" className="submitButton" onClick={this.handleJobAdd} style={this.props.submitAvailability}>Generate Districting Comparisons</Button>{' '}
      <p className='warnText'>{this.props.warnText}</p>
      </div>

    )
  }
}
