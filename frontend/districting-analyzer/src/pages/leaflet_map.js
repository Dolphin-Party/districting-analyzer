import React, { Component, useState } from 'react'
import { Map, TileLayer, GeoJSON, LayersControl} from 'react-leaflet'
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown'
import RangeSlider from 'react-bootstrap-range-slider';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';
import Input from '@material-ui/core/Input';


type State = {
  lat: number,
  lng: number,
  zoom: number,
}

export default class LeafletMap extends Component<{}, State> {
  constructor(props) {
    super(props);
    this.state = {
      originalState: "True",
      lat: 37.090240,
      lng: -95.712891,
      zoom: 5,
      stateName: " ",
      stateDensity: " ",
    };
    this.resetMap = this.resetMap.bind(this);
  }

  onEachFeature = (component, feature, layer) => {
    layer.on({
      mouseover: this.highlightFeature,
      mouseout: this.resetHighlight.bind(null, component),
      click: this.clickSelectState.bind(null, component)
    });
  }

  highlightFeature = (e) => {
    var layer = e.target;
    const stateName= e.target.feature.properties.name
    const stateDensity=e.target.feature.properties.density
    this.setState({stateName: stateName, stateDensity: stateDensity});

    layer.setStyle({
      weight: 5,
      color: '#3B2B59',
      dashArray: '',
      fillColor: 'white',
      fillOpacity: 0.2
    });
  }

  resetHighlight = (component, e) => {
    var layer = e.target;

    layer.setStyle({
      color: '#3B2B59',
      weight: 2,
      fillColor: "#3B2B59",
      fillOpacity: 0.5,
      dashArray: '3'
    });
  }

  clickSelectState = (component, e) => {
    var layer = e.target;
    const stateName= e.target.feature.properties.name
    const stateDensity=e.target.feature.properties.density
    const stateLat = e.target.feature.properties.lat
    const stateLng = e.target.feature.properties.lng
    this.setState({originalState: "False", zoom: 7, lat:stateLat, lng:stateLng, stateName: stateName, stateDensity: stateDensity});
    }


  resetMap(){
    this.setState(state => ({originalState: "True", lat: 37.090240, lng: -95.712891, zoom: 5, stateName: " ", stateDensity: " "}));
  }


  render() {
    const position = [this.state.lat, this.state.lng]
    const stateName = [this.state.name]
    const mapboxAccessToken = 'pk.eyJ1IjoiZG9scGhpbi1wYXJ0eSIsImEiOiJja2ZwcmpoemwwbW8zMnJuNTVha2I3aHV0In0.Y1agteWswtHBaLViI2UWig';
    const useStyles = makeStyles({
      root: {
        width: 250,
      },
      input: {
        width: 42,
      },
    });

    return (
      <div className='leaflet-container'>
        <Map center={position} zoom={this.state.zoom} ref={this.mapRef}>
        <div className='map-information'>
          <p className='state-info'> State Name: {this.state.stateName}</p>
          <p className='state-info'> State Density: {this.state.stateDensity}</p>
        </div>
        <button className="reset-button" onClick={this.resetMap}>Reset Map</button>{' '}
        <TileLayer
          attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url={'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=' + mapboxAccessToken}
          id='mapbox/light-v9'
        ></TileLayer>
        <GeoJSON
          data={getGeoJson()}
          style={() => ({
          color: '#3B2B59',
          weight: 2,
          fillColor: "#3B2B59",
          fillOpacity: 0.5,
          dashArray: '3',
          })}
          state={() => ({
            name: 'blorp'
          })}
          onEachFeature={this.onEachFeature.bind(null, this)}
          ></GeoJSON>
        </Map>
      </div>
    )
  }
}

function getGeoJson() {
return {"type":"FeatureCollection","features":
  [
  {"type":"Feature","id":"51","properties":{"name":"Virginia","density":204.5, "lat": 37.4316, "lng": -78.024902},"geometry":{"type":"MultiPolygon","coordinates":[[[[-75.397659,38.013497],[-75.244304,38.029928],[-75.375751,37.860142],[-75.512674,37.799896],[-75.594828,37.569865],[-75.802952,37.197433],[-75.972737,37.120755],[-76.027507,37.257679],[-75.939876,37.564388],[-75.671506,37.95325],[-75.397659,38.013497]]],[[[-76.016553,37.95325],[-75.994645,37.95325],[-76.043938,37.95325],[-76.016553,37.95325]]],[[[-78.349729,39.464886],[-77.82942,39.130793],[-77.719881,39.322485],[-77.566527,39.306055],[-77.456988,39.223901],[-77.456988,39.076023],[-77.248864,39.026731],[-77.117418,38.933623],[-77.040741,38.791222],[-77.128372,38.632391],[-77.248864,38.588575],[-77.325542,38.446175],[-77.281726,38.342113],[-77.013356,38.374975],[-76.964064,38.216144],[-76.613539,38.15042],[-76.514954,38.024451],[-76.235631,37.887527],[-76.3616,37.608203],[-76.246584,37.389126],[-76.383508,37.285064],[-76.399939,37.159094],[-76.273969,37.082417],[-76.410893,36.961924],[-76.619016,37.120755],[-76.668309,37.065986],[-76.48757,36.95097],[-75.994645,36.923586],[-75.868676,36.551154],[-79.510841,36.5402],[-80.294043,36.545677],[-80.978661,36.562108],[-81.679709,36.589492],[-83.673316,36.600446],[-83.136575,36.742847],[-83.070852,36.852385],[-82.879159,36.890724],[-82.868205,36.978355],[-82.720328,37.044078],[-82.720328,37.120755],[-82.353373,37.268633],[-81.969987,37.537003],[-81.986418,37.454849],[-81.849494,37.285064],[-81.679709,37.20291],[-81.55374,37.208387],[-81.362047,37.339833],[-81.225123,37.235771],[-80.967707,37.290541],[-80.513121,37.482234],[-80.474782,37.421987],[-80.29952,37.509618],[-80.294043,37.690357],[-80.184505,37.849189],[-79.998289,37.997066],[-79.921611,38.177805],[-79.724442,38.364021],[-79.647764,38.594052],[-79.477979,38.457129],[-79.313671,38.413313],[-79.209609,38.495467],[-78.996008,38.851469],[-78.870039,38.763838],[-78.404499,39.169131],[-78.349729,39.464886]]]]}},

  {"type":"Feature","id":"37","properties":{"name":"North Carolina","density":198.2,"lat": 35.782169, "lng":-80.793457},"geometry":{"type":"Polygon","coordinates":[[[-80.978661,36.562108],[-80.294043,36.545677],[-79.510841,36.5402],[-75.868676,36.551154],[-75.75366,36.151337],[-76.032984,36.189676],[-76.071322,36.140383],[-76.410893,36.080137],[-76.460185,36.025367],[-76.68474,36.008937],[-76.673786,35.937736],[-76.399939,35.987029],[-76.3616,35.943213],[-76.060368,35.992506],[-75.961783,35.899398],[-75.781044,35.937736],[-75.715321,35.696751],[-75.775568,35.581735],[-75.89606,35.570781],[-76.147999,35.324319],[-76.482093,35.313365],[-76.536862,35.14358],[-76.394462,34.973795],[-76.279446,34.940933],[-76.493047,34.661609],[-76.673786,34.694471],[-76.991448,34.667086],[-77.210526,34.60684],[-77.555573,34.415147],[-77.82942,34.163208],[-77.971821,33.845545],[-78.179944,33.916745],[-78.541422,33.851022],[-79.675149,34.80401],[-80.797922,34.820441],[-80.781491,34.935456],[-80.934845,35.105241],[-81.038907,35.044995],[-81.044384,35.149057],[-82.276696,35.198349],[-82.550543,35.160011],[-82.764143,35.066903],[-83.109191,35.00118],[-83.618546,34.984749],[-84.319594,34.990226],[-84.29221,35.225734],[-84.09504,35.247642],[-84.018363,35.41195],[-83.7719,35.559827],[-83.498053,35.565304],[-83.251591,35.718659],[-82.994175,35.773428],[-82.775097,35.997983],[-82.638174,36.063706],[-82.610789,35.965121],[-82.216449,36.156814],[-82.03571,36.118475],[-81.909741,36.304691],[-81.723525,36.353984],[-81.679709,36.589492],[-80.978661,36.562108]]]}},

  {"type":"Feature","id":"05","properties":{"name":"Arkansas","density":56.43,  "lat": 34.799999, "lng": -92.199997},"geometry":{"type":"Polygon","coordinates":[[[-94.473842,36.501861],[-90.152536,36.496384],[-90.064905,36.304691],[-90.218259,36.184199],[-90.377091,35.997983],[-89.730812,35.997983],[-89.763673,35.811767],[-89.911551,35.756997],[-89.944412,35.603643],[-90.130628,35.439335],[-90.114197,35.198349],[-90.212782,35.023087],[-90.311367,34.995703],[-90.251121,34.908072],[-90.409952,34.831394],[-90.481152,34.661609],[-90.585214,34.617794],[-90.568783,34.420624],[-90.749522,34.365854],[-90.744046,34.300131],[-90.952169,34.135823],[-90.891923,34.026284],[-91.072662,33.867453],[-91.231493,33.560744],[-91.056231,33.429298],[-91.143862,33.347144],[-91.089093,33.13902],[-91.16577,33.002096],[-93.608485,33.018527],[-94.041164,33.018527],[-94.041164,33.54979],[-94.183564,33.593606],[-94.380734,33.544313],[-94.484796,33.637421],[-94.430026,35.395519],[-94.616242,36.501861],[-94.473842,36.501861]]]}}
  ]
}}