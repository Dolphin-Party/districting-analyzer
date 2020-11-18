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


export default class RequestJobTab extends Component<{}, State> {
  render() {

    const [value, setValue] = [30,30]
    const handleSliderChange = (event, newValue) => {
      setValue(newValue);
    };
    const handleInputChange = (event) => {
      setValue(event.target.value === '' ? '' : Number(event.target.value));
    };
    const handleBlur = () => {
    if (value < 0) {
      setValue(0);
    } else if (value > 100) {
      setValue(100);
    }
  };


    return (
      <div className='job-request'>
      <p className='title'>Generating Districtings</p>
      <p># of Random Districtings</p>
      <InputSlider></InputSlider>
      <p>Compactness %</p>
      <CompactnessSlider></CompactnessSlider>
      <p>Target Demographic VAP %</p>
      <InputSlider></InputSlider>
      <Button variant="button" className="button">Generate Districting Comparisons</Button>{' '}
      </div>

    )
  }
}
