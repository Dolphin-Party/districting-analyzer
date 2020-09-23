import React, { Component } from 'react'
import { Map, TileLayer, Marker, Popup } from 'react-leaflet'

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
        <div className='leaflet-container'>
          <Map center={position} zoom={this.state.zoom}>
            <TileLayer
              attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker position={position}>
              <Popup>
                A pretty CSS3 popup. <br /> Easily customizable.
              </Popup>
            </Marker>
          </Map>
        </div>
        <div className='map_information'>
          <p className='title'>Information & Data</p>
          <p className='category'> Selected State </p>
        </div>
        <div className="map_filter">
        </div>
      </div>
    )
  }
}
