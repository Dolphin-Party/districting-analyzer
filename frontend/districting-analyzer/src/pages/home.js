import React, { Component } from 'react'
import { Map, TileLayer, Marker, Popup } from 'react-leaflet'
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown'

type State = {
  lat: number,
  lng: number,
  zoom: number,
}

export default class Home extends Component<{}, State> {
  state = {
    lat: 37.090240,
    lng: -95.712891,
    zoom: 5,
  }


  render() {
    const position = [this.state.lat, this.state.lng]
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
            <Dropdown.Toggle variant="success" className="button" id="dropdown-basic">
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
          <Dropdown.Toggle variant="success" className="button" id="dropdown-basic">
            Main-In Voting?
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item href="#/action-1">Precincts That Allow</Dropdown.Item>
            <Dropdown.Item href="#/action-2">Precincts That DON'T Allow</Dropdown.Item>
          </Dropdown.Menu>
      </Dropdown>
      <Dropdown  className="button-space" >
        <Dropdown.Toggle variant="success" className="button" id="dropdown-basic">
          Display Voting Locations
        </Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item href="#/action-1">On</Dropdown.Item>
          <Dropdown.Item href="#/action-2">Off</Dropdown.Item>
        </Dropdown.Menu>
    </Dropdown>
        </div>


        <div className='map_information'>
          <p className='title'>Information & Data</p>
          <p className='category'> State Name: </p>
          <p className='category'> Population Number: </p>
          <p className='category'> Amount of Districts:</p>
          <p className='category'> Red/Blue/Swing State: </p>
          <p className='category'> -----------------------------------------------------------</p>
          <p className='category'> Selected (District/Precinct) </p>
          <p className='category'> District Population </p>
        </div>


        <div className='leaflet-container'>
          <Map center={position} zoom={this.state.zoom}>
            <TileLayer
              attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"
            />
            <Marker position={position}>
              <Popup>
                A pretty CSS3 popup. <br /> Easily customizable.
              </Popup>
            </Marker>
          </Map>
        </div>

        <div className="map_filter">
          <Button variant="primary" className="button">View Current Districting</Button>{' '}
            <Dropdown className="button-space" >
              <Dropdown.Toggle variant="success" className="button" id="dropdown-basic">
                View Past Districtings
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item href="#/action-1">2018</Dropdown.Item>
                <Dropdown.Item href="#/action-2">2017</Dropdown.Item>
              </Dropdown.Menu>
          </Dropdown>
          <Button variant="primary" className="button">Precinct Comparison</Button>{' '}
          <Button variant="primary" className="button">State Comparison</Button>{' '}
          <Button variant="primary" className="special-button">Generate Randomized Districting Comparison</Button>{' '}
          <Button variant="primary" className="button">Export Graphs & Data</Button>{' '}
        </div>
      </div>
    )
  }
}
