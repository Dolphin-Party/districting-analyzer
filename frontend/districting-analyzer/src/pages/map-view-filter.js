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
              Select State
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item href="#/action-1">Virginia</Dropdown.Item>
              <Dropdown.Item href="#/action-2">North Carolina</Dropdown.Item>
              <Dropdown.Item href="#/action-2">Arkansas</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>

        <Dropdown className="button-space" >
          <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
            District Boundaries
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item href="#/action-1">Display</Dropdown.Item>
            <Dropdown.Item href="#/action-2">Don't Display</Dropdown.Item>
          </Dropdown.Menu>
      </Dropdown>

            <Dropdown className="button-space" >
              <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
                Demographic Heat Map
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item href="#/action-1">Black or African American</Dropdown.Item>
                <Dropdown.Item href="#/action-2">Non-Hispanic White</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Hispanic and Latino</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Asian</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Native Americans and Alaska Natives</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Native Hawaiians and Other Pacific Islanders</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Two or more races</Dropdown.Item>
                <Dropdown.Item href="#/action-3">Disable Demographic Heat Map</Dropdown.Item>
              </Dropdown.Menu>
          </Dropdown>
          <Dropdown className="button-space" >
            <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
              Random Districting
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item href="#/action-1">Average Plan</Dropdown.Item>
              <Dropdown.Item href="#/action-2">Extreme Plan</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
        </div>
      </div>
    )
  }
}
