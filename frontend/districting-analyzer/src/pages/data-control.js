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

import BoxWhiskerPlot from '../components/boxwhiskerplot'


export default class DataControl extends Component<{}, State> {

  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div>
      <div className="map_filter">
        <p>Legend: Pls style me tomorrow</p>
      </div>
      <div className='box-whisker-plot-section'>
        <BoxWhiskerPlot boxWhiskerData={this.props.boxWhiskerData} enactedPlan={this.props.enactedPlan}></BoxWhiskerPlot>
      </div>
        </div>
      )
    }
  }
