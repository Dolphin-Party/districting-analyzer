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



export default class MapViewFilter extends Component<{}, State> {
  render() {

    return (
      <div>
        <div className="map_filter">
            <Dropdown className="button-space" >
              <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
                Racial/Ethnic Demographic
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item href="#/action-1">Black or African American</Dropdown.Item>
                <Dropdown.Item href="#/action-2">Non-Hispanic White</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Hispanic and Latino</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Asian</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Native Americans and Alaska Natives</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Native Hawaiians and Other Pacific Islanders</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Two or more races</Dropdown.Item>
              </Dropdown.Menu>
          </Dropdown>
          <Dropdown className="button-space" >
            <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
              Change Precinct Coloring
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item href="#/action-1">2016 Election</Dropdown.Item>
              <Dropdown.Item href="#/action-2">2012 Election</Dropdown.Item>
              <Dropdown.Item href="#/action-3">2008 Election</Dropdown.Item>
              <Dropdown.Item href="#/action-3">Demographics</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
        <Dropdown className="button-space" >
          <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
            Mail-In Voting?
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item href="#/action-1">Precincts That Allow</Dropdown.Item>
            <Dropdown.Item href="#/action-2">Precincts That DON'T Allow</Dropdown.Item>
          </Dropdown.Menu>
      </Dropdown>
      <Dropdown  className="button-space" >
        <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
          Display Voting Locations
        </Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item href="#/action-1">On</Dropdown.Item>
          <Dropdown.Item href="#/action-2">Off</Dropdown.Item>
        </Dropdown.Menu>
    </Dropdown>
        </div>
        </div>
      )
    }
  }
