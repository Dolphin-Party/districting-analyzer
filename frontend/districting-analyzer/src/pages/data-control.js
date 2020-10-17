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


export default class DataControl extends Component<{}, State> {
  render() {




    return (
      <div>
      <div className="map_filter">
        <Button variant="primary" className="button">View Current Districting</Button>{' '}
          <Dropdown className="button-space" >
            <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
              View Past Districtings
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item href="#/action-1">2018</Dropdown.Item>
              <Dropdown.Item href="#/action-2">2017</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
        <Button variant="primary" className="button">Precinct Comparison</Button>{' '}
        <Button variant="primary" className="button">State Comparison</Button>{' '}
        <Button variant="primary" className="button">Export Graphs & Data</Button>{' '}
      </div>


      <div className='data_section'>

      </div>
        </div>
      )
    }
  }
