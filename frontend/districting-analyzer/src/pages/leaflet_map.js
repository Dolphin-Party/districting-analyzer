import React, { Component, useState, useRef} from 'react'
import axios from 'axios';
import hash from 'object-hash'
import { Link } from "react-router-dom";
import L from "leaflet";
import { Map, TileLayer, GeoJSON, LayersControl, LayerGroup, Marker} from 'react-leaflet'
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup'
import ToggleButton from 'react-bootstrap/ToggleButton'
import Dropdown from 'react-bootstrap/Dropdown'
import { makeStyles } from '@material-ui/core/styles';
import virginiaPrecincts from '../assets/geojson/VirginiaPrecincts_.json'
import northCarolinaPrecincts from '../assets/geojson/NorthCarolinaPrecincts_.json'
import arkansasPrecincts from '../assets/geojson/ArkansasPrecinctData.json'
import teamStates from '../assets/geojson/teamstates.json'
import congressional from '../assets/geojson/us_congressional.json'



const { Overlay } = LayersControl;
const precinctLayerRef = useRef();
const center = [51, 0];
const mapRef = useRef();
const firstOverlayRef = useRef();
const stateRef = useRef();
const secondOverlayRef = useRef();
const icon = L.icon({
  iconSize: [25, 41],
  iconAnchor: [10, 41],
  popupAnchor: [2, -40],
  iconUrl: "https://unpkg.com/leaflet@1.6/dist/images/marker-icon.png",
  shadowUrl: "https://unpkg.com/leaflet@1.6/dist/images/marker-shadow.png"
});

const layerControl= document.getElementsByClassName('leaflet-control-layers');
const heatMapLegend= document.getElementsByClassName('heatMapLegend');
const virginiaPrecinctLayer= document.getElementsByClassName('virginiaPrecinctLayer');

export default class LeafletMap extends Component<{}, State> {
  constructor(props) {
    super(props);

    this.state = {
      originalState: "True",
      lat: 37.090240,
      lng: -95.712891,
      zoom: 5,
      stateName: "",
      stateDensity: " ",
      statePopulation: " ",
      stateNumDistricts: " ",
      stateSelected: false,
      states: teamStates,
      isLoading: true,
      precincts: {arkansas: arkansasPrecincts, virginia: virginiaPrecincts, northCarolina: northCarolinaPrecincts},
      precinctDisplay: false,
      congressional: congressional,
      districts: {},
      demographics: {
        0:'black',
        1:'whiteNonHispanic',
        2:'hispanic',
        3:'asian',
        4:'americanIndian',
        5:'pacific',
        6:'twoOrMoreRaces'
      },
      mapRef: mapRef,
      heatMapStyles: {
        0:{backgroundColor: '#FFEDA0'},
        1:{backgroundColor: '#FED976'},
        2:{backgroundColor: '#FEB24C'},
        3:{backgroundColor: '#FD8D3C'},
        4:{backgroundColor: '#FC4E2A'},
        5:{backgroundColor: '#E31A1C'},
        6:{backgroundColor: '#BD0026'},
        7:{backgroundColor: '#a30021'},
        8:{backgroundColor: '#85001b'},
        9:{backgroundColor: '#6e0016'},
      },
      heatMapOn: false,
      heatMapButton: {backgroundColor: '#3B2B59'},
      heatMapLegendStyle: {visibility: 'hidden'},
      precinctColor: '#3B2B59',
      stateDefaultStyle: {
        color: '#3B2B59',
        weight: 2,
        fillColor: "#3B2B59",
        fillOpacity: 0.5,
        dashArray: '3'
      },
      stateHighlightStyle: {
        weight: 5,
        color: '#3B2B59',
        dashArray: '',
        fillColor: 'white',
        fillOpacity: 0.2
      },
      precinctDefaultStyle: {
        color: '#3B2B59',
        weight: 1,
        fillColor: "transparent",
        fillOpacity: 0.5,
      },
      precinctHighlightStyle: {
        color: '#3B2B59',
        weight: 1,
        fillColor: "white",
        fillOpacity: 0.5,
      },
    };
    this.resetMap = this.resetMap.bind(this);
    this.resetPrecinct = this.resetPrecinct.bind(this);
    this.dropdownStateSelect = this.dropdownStateSelect.bind(this);
    this.heatMapOn = this.heatMapOn.bind(this);
    this.getPrecinctColor = this.getPrecinctColor.bind(this)
    this.getStateData = this.getStateData.bind(this)
  }

  getStateData(){
    return this.props.stateData
  }

  onEachFeature = (component, feature, layer) => {
    layer.on({
      mouseover: this.highlightFeature,
      mouseout: this.resetHighlight.bind(null, component),
      click: this.clickSelectState.bind(null, component),
    });
  }

  highlightFeature = (e) => {
    var layer = e.target;
    const stateName= e.target.feature.properties.name
    const stateDensity=e.target.feature.properties.density
    const statePopulation = e.target.feature.properties.population
    const stateNumDistricts = e.target.feature.properties.numDistricts
    this.setState({stateName: stateName, stateDensity: stateDensity, statePopulation: statePopulation, stateNumDistricts:stateNumDistricts});
    layer.setStyle(this.state.stateHighlightStyle);
  }

  resetHighlight = (component, e) => {
    var layer = e.target;
    layer.setStyle(this.state.stateDefaultStyle);
  }

  clickSelectState = (component, e) => {
    if(e.properties == undefined){
      var e = e.target.feature
    }
    const stateName= e.properties.name
    const stateDensity=e.properties.density
    const stateLat = e.properties.lat
    const stateLng = e.properties.lng
    const statePopulation = e.properties.population
    const stateNumDistricts = e.properties.numDistricts
    this.stateLayerControlToggle(stateName)
    this.props.onStateSelect(stateName);
    this.setState({originalState: "False", zoom: 7, stateSelected: true, lat:stateLat, lng:stateLng, stateName: stateName, stateDensity: stateDensity, statePopulation: statePopulation, stateNumDistricts:stateNumDistricts});
    }

    dropdownStateSelect(ev, stateNum){
      var geoJSON = this.state.states
      var e = geoJSON['features'][stateNum]
      this.clickSelectState(null, e)
    }

    stateLayerControlToggle(stateName){
      if(stateName=='Virginia'){
          layerControl[0].style.visibility = 'visible';
          layerControl[1].style.visibility = 'hidden';
          layerControl[2].style.visibility = 'hidden';
      }else if(stateName=='North Carolina'){
          layerControl[1].style.visibility = 'visible';
          layerControl[0].style.visibility = 'hidden';
          layerControl[2].style.visibility = 'hidden';
      }else{
          layerControl[2].style.visibility = 'visible';
          layerControl[1].style.visibility = 'hidden';
          layerControl[0].style.visibility = 'hidden';
      }
    }

    dropdownDemographicSelect(ev, demId){
      this.props.onDemSelect(this.state.demographics[demId]);
    }

  onEachPrecinct = (component, feature, layer,stateName) => {
    layer.on({
      mouseover: this.highlightPrecinct,
      mouseout: this.resetPrecinct.bind(null, component),
    });
  }

  highlightPrecinct = (e) => {
    var layer = e.target;
    const stateName= e.target.feature.properties.Precinct
    this.setState({stateName: stateName});
    layer.setStyle(this.state.precinctHighlightStyle);
  }

  resetPrecinct = (component, e) => {
    var layer = e.target;
    layer.setStyle(this.state.precinctDefaultStyle)
  }

  getPrecinctColor = (component, feature, layer) => {
    if(this.state.heatMapOn){
      const totalPop = feature.properties.demographicData.totalPop
      const targetDem = this.props.currDem
      var demData = feature.properties.demographicData
      var targetPop = this.getTargetDemographicPopulation(targetDem, demData)
      const d = (targetPop/totalPop)
      var color = d > .90 ? '#6e0016' :
         d > .80  ? '#85001b' :
         d > .70  ? '#a30021' :
         d > .60  ? '#BD0026' :
         d > .50  ? '#E31A1C' :
         d > .40  ? '#FC4E2A' :
         d > .30  ? '#FD8D3C' :
         d > .20  ? '#FEB24C' :
         d > .10  ? '#FED976' :
                  '#FFEDA0';
      return {
        weight: 2,
        color: color,
        dashArray: '',
        fillColor: color,
        fillOpacity: 1.0,
      }
    }else{
      return this.state.precinctDefaultStyle
    }
  }

  getTargetDemographicPopulation(targetDem, targetPop){
    switch(targetDem){
      case 'black':
        return targetPop.blackPop
        break;
      case 'whiteNonHispanic':
        return targetPop.whitePop
        break;
      case 'hispanic':
        return targetPop.hispaPop
        break;
      case 'asian':
        return targetPop.asianPop
        break;
      case 'americanIndian':
        return targetPop.nativPop
        break;
      case 'pacific':
        return targetPop.hawaiPop
        break;
      case 'twoOrMoreRaces':
        return targetPop.otherPop
        break;
    }
  }

  resetMap(){
    for(var i=0; i<3; i++){
      layerControl[i].style.visibility = 'hidden';
    }
    this.setState(state => ({originalState: "True", stateSelected: false, lat: 37.090240, lng: -95.712891, zoom: 5, stateName: "", stateDensity: " "}));
    this.props.onReset();
  }

  heatMapOn(e){
    if(this.state.heatMapOn){
      console.log("turning off heat map")
      this.setState({heatMapLegendStyle: {visibility: 'hidden'}, heatMapOn: false, heatMapButton: {backgroundColor: '#3B2B59', borderColor: 'grey', borderWidth: 'thick'}})
    }else{
      if(this.props.currDem != null){
        this.setState({heatMapLegendStyle: {visibility: 'visible'}, heatMapOn: true, heatMapButton: {backgroundColor: '#3B2B59', borderColor: '#39ff14', borderWidth: 'thick'}})
      }else{
        alert("Please select target demographic for heat map")
      }
    }
  }

  render() {
    const states = this.state.states;
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
    const heatMapGrades = [0,10,20,30,40,50,60,70,80,90]
    const dropdownStates = ['Virginia', 'North Carolina', 'Arkansas']
    const camelcaseStates = ['virginia', 'northCarolina']
    const dropdownDemographics = ['Black or African American', 'Non-Hispanic White', 'Hispanic and Latino', 'Asian', 'Native Americans and Alaska Natives','Native Hawaiians and Other Pacific Islanders', 'Two or more races' ]

    return (
      <div className='leftside'>
        <div className="map_filter">
          <Dropdown className="button-space" >
            <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
              Select State
            </Dropdown.Toggle>
            <Dropdown.Menu>
                {dropdownStates.map((stateName, i) =>
                  <Dropdown.Item key={i} onClick={(e) => this.dropdownStateSelect(e,i)}>{stateName}</Dropdown.Item>
                )}
            </Dropdown.Menu>
        </Dropdown>
            <Dropdown className="button-space" >
              <Dropdown.Toggle variant="button" className="button" id="dropdown-basic">
                Select Demographic
              </Dropdown.Toggle>
              <Dropdown.Menu>
                {dropdownDemographics.map((demName, i) =>
                  <Dropdown.Item key={i} onClick={(e) => this.dropdownDemographicSelect(e,i)}>{demName}</Dropdown.Item>
                )}
              </Dropdown.Menu>
          </Dropdown>
          <Button className="button" style={this.state.heatMapButton} onClick={(e) => this.heatMapOn(e)}>Heat Map</Button>
          <p className='currMap-Info'> Selected State: {this.props.currState}</p>
          <p className='currMap-Info'> Selected Demographic: {this.props.currDem}</p>
        </div>

      <div className='leaflet-container'>
        <Map center={position} zoom={this.state.zoom} ref={mapRef} onClick={this.mapClick}>
        <div className='map-information'>
          <p className='state-info'> State Name: {this.state.stateName}</p>
          <p className='state-info'> State Density: {this.state.stateDensity}</p>
          <p className='state-info'> Population: {this.state.statePopulation}</p>
          <p className='state-info'> Number of Districts: {this.state.stateNumDistricts}</p>
        </div>
        <div className='heatMapLegend' style={this.state.heatMapLegendStyle}>
          {heatMapGrades.map((grade, i, array) =>
            <div className='text'>
              <i style={this.state.heatMapStyles[i]}></i>
              {(heatMapGrades[i+1])
                ? <p key={i}>{grade}% - {heatMapGrades[i+1]}% </p>
                : <p key={i}>{grade}% +</p>
              }
            </div>
          )}
        </div>
        <button className="reset-button" onClick={this.resetMap} >Reset Map</button>{' '}
        <TileLayer
          attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url={'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=' + mapboxAccessToken}
          id='mapbox/light-v9'
        ></TileLayer>
        <GeoJSON
          ref={stateRef}
          key={hash(this.props.stateData)}
          data={this.props.stateData}
          style={this.state.stateDefaultStyle}
          state={() => ({
            name: ''
          })}
          onEachFeature={this.onEachFeature.bind(null, this)}
          ></GeoJSON>
        {camelcaseStates.map((stateName, i) =>
          <LayersControl key={i} position="topright">
          <Overlay name="Precinct Borders">
                <LayerGroup  ref={firstOverlayRef}>
              <GeoJSON key="precinctLayer"
                data={this.state.precincts[stateName]}
                style={this.getPrecinctColor.bind(null, this)}
                onEachFeature={this.onEachPrecinct.bind(null, this)}
              ></GeoJSON>
            </LayerGroup>
          </Overlay>
          <Overlay name="District Borders">
              <LayerGroup id="virginiaDistricts" ref={secondOverlayRef}>
                <GeoJSON
                  data={this.state.congressional}
                  style={this.state.stateDefaultStyle}>
                </GeoJSON>
          </LayerGroup>
        </Overlay>
            </LayersControl>
        )}
            <LayersControl position="topright" id="arkansasLayers">
            <Overlay name="Precinct Borders">
                  <LayerGroup id="arkansasPrecincts" ref={firstOverlayRef}>
                <GeoJSON key="precinctLayer"
                  id='precinctLayer'
                  data={this.state.precincts.arkansas}
                  style={() => ({
                  color: this.state.precinctColor,
                  weight: 1,
                  fillColor: "transparent",
                  fillOpacity: 0.5,
                  })}
                  onEachFeature={this.onEachPrecinct.bind(null, this)}
                ></GeoJSON>
              </LayerGroup>
            </Overlay>
            <Overlay name="District Borders">
                <LayerGroup id="lg2" ref={secondOverlayRef}>
            </LayerGroup>
          </Overlay>
              </LayersControl>
        </Map>
      </div>
    </div>
    )
  }
}
