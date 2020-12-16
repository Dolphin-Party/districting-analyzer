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
import { makeStyles} from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import purple from '@material-ui/core/colors/purple';
import { green } from '@material-ui/core/colors';
import { FormControl, FormLabel, RadioGroup, FormControlLabel, Radio, Checkbox} from '@material-ui/core';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import virginiaPrecincts from '../assets/geojson/VA_formatted_precincts.json'
import northCarolinaPrecincts from '../assets/geojson/NC_formatted_precincts.json'
import arkansasPrecincts from '../assets/geojson/AK_formatted_precincts.json'
import teamStates from '../assets/geojson/teamstates.json'
import virginiaDistricts from '../assets/geojson/virginiaDistricts.json'
import arkansasDistricts from '../assets/geojson/arkansasDistricts.json'
import northCarolinaDistricts from '../assets/geojson/northCarolinaDistricts.json'
// import AK_frontend_precincts from '../assets/geojson/AK_frontend_precincts/AK_frontend_precincts.json'

import radioButton from '../components/radioButton'
import union from '@turf/union'
import sample_districting from '../assets/geojson/sample_districting.json'
import * as turf from '@turf/turf'
import * as geojsonMerge from '@mapbox/geojson-merge'

import NC_random from '../assets/geojson/NC_districting_1.json'
import NC_average from '../assets/geojson/NC_districting_3.json'
import NC_extreme from '../assets/geojson/NC_districting_5.json'
import NC_minimum from '../assets/geojson/NC_districting_7.json'

import VA_random from '../assets/geojson/VA_districting_9.json'
import VA_average from '../assets/geojson/VA_districting_12.json'

const { Overlay } = LayersControl;
const precinctLayerRef = useRef();
const center = [51, 0];
const mapRef = useRef();
const firstOverlayRef = useRef();
const stateRef = useRef();
const secondOverlayRef = useRef();
const thirdOverlayRef = useRef();
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


const theme = createMuiTheme({
  palette: {
    primary: {
      main: '#0000ff',
    },
    secondary: {
      main: '#ffa500',
    },
    default: {
      main: '#ff0000',
    },
    inherit: {
      main: '#008000',
    }
  },
});

const RandomCheckbox = withStyles({
  root: {
    color: "#00FF00",
    '&$checked': {
      color: "#00FF00",
    },
  },
  checked: {},
})(props => <Checkbox color="default" {...props} />);

const AverageCheckbox = withStyles({
  root: {
    color: '#0000ff',
    '&$checked': {
      color: '#0000ff',
    },
  },
  checked: {},
})(props => <Checkbox color="default" {...props} />);

const ExtremeCheckbox = withStyles({
  root: {
    color: "#ffa500",
    '&$checked': {
      color: "#ffa500",
    },
  },
  checked: {},
})(props => <Checkbox color="default" {...props} />);

const MinimumCheckbox = withStyles({
  root: {
    color: "#990000",
    '&$checked': {
      color: "#990000",
    },
  },
  checked: {},
})(props => <Checkbox color="default" {...props} />);


const themeDict = {
  Random: "primary",
  Average: "secondary",
  Extreme: "palette.inherit",
  Minimum: "palette.default"
}

export default class LeafletMap extends Component<{}, State> {
  constructor(props) {
    super(props);

    this.state = {
      originalState: "True",
      lat: 37.090240,
      lng: -95.712891,
      zoom: 5,
      mapInfo: {text1: "", text2: "", text3: "", text4: "", text5: "", text6: "", text7: "", text8: ""},
      demographicDict: {
        black: "Black/African American",
        whiteNonHispanic:'White Non-Hispanic',
        hispanic:'Hispanic',
        asian:'Asian',
        americanIndian:'Native American',
        pacific:'Pacific',
        twoOrMoreRaces:'Two Or More Races'
      },
      stateDensity: " ",
      statePopulation: " ",
      stateNumDistricts: " ",
      stateSelected: false,
      states: this.props.stateData,
      isLoading: true,
      precincts: {arkansas: arkansasPrecincts, virginia: virginiaPrecincts, northCarolina: northCarolinaPrecincts},
      // precincts: {arkansas: arkansasPrecincts, virginia: [], northCarolina: []},
      // districts: {arkansas: [], virginia: [], northCarolina: []},
      districts: {arkansas: arkansasDistricts, virginia: virginiaDistricts, northCarolina: northCarolinaDistricts},
      stateDict: {51: 0, 37:1, 5:2},
      precinctDisplay: false,
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
      heatMapMenuStyle:  {display: 'none'},
      heatMapLegendStyle: {display: 'none'},
      heatMapLegendStyleAverage: {display: 'none'},
      heatMapOption: 'original',
      districtingMenuStyle: {visibility: 'visible'},
      districtingState: this.props.districtingState,
      districtingOn: false,
      currentDist: [NC_random, NC_average, NC_extreme, NC_minimum],
      selectedDistricting: null,
      districtingJobId: this.props.districtingJobId,
      checkedDist: {
        Random: false,
        Average: false,
        Extreme: false,
        Minimum: false
      },
      precinctColor: '#3B2B59',
      stateDefaultStyle: {
        color: '#3B2B59',
        weight: 2,
        fillColor: "#3B2B59",
        fillOpacity: 0.5,
        dashArray: '3'
      },
      districtDefaultStyle:{
        color: '#3B2B59',
        weight: 2,
        fillColor: "transparent",
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
      districtingStyle:  {
        0: {
        color: '#00FF00',
        weight: 2,
        fillColor: "transparent",
        fillOpacity: 0,
        },
        1: {
        color: '#0000ff',
        weight: 2,
        fillColor: "transparent",
        fillOpacity: 0,
        },
        2: {
        color: '#ffa500',
        weight: 2,
        fillColor: "transparent",
        fillOpacity: 0,
        },
        3: {
        color: '#990000',
        weight: 2,
        fillColor: "transparent",
        fillOpacity: 0,
        }
      },
      defaultDistrictingStyle: {
        color: 'transparent',
        fillColor: "transparent",
      },
      stateAverage: {
        Virginia: {
          black: '19.4',
          whiteNonHispanic: '68.6',
          hispanic: '3.2',
          asian: '5.5',
          americanIndian: '0.4',
          pacific: '0.1',
          twoOrMoreRaces: '2.9'
        },
        Arkansas: {
          black: '15.4',
          whiteNonHispanic: '77.0',
          hispanic: '3.2',
          asian: '1.2',
          americanIndian: '0.8',
          pacific: '0.2',
          twoOrMoreRaces: '2.0'
        },
        'North Carolina': {
          black: '22.1',
          whiteNonHispanic: '71.2',
          hispanic: '9.1',
          asian: '2.8',
          americanIndian: '1.6',
          pacific: '0.2',
          twoOrMoreRaces: '2.0'
        }
      }
    };
    this.resetMap = this.resetMap.bind(this);
    this.resetPrecinct = this.resetPrecinct.bind(this);
    this.dropdownStateSelect = this.dropdownStateSelect.bind(this);
    this.heatMapOn = this.heatMapOn.bind(this);
    this.getPrecinctColor = this.getPrecinctColor.bind(this)
    this.getStateData = this.getStateData.bind(this)
    this.componentDidUpdate = this.componentDidUpdate.bind(this)
    this.getdistrictingStyle = this.getdistrictingStyle.bind(this)
    this.districtingOn = this.districtingOn.bind(this)
    this.districtingOff = this.districtingOff.bind(this)
    this.heatMapOption = this.heatMapOption.bind(this)
  }

  componentDidUpdate(prevProps){
    if (this.props.districtingState !== prevProps.districtingState){
      this.dropdownStateSelect(null,this.state.stateDict[this.props.districtingState])
    }
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
    console.log("Highlighting, ", layer.feature.properties.name)
    this.updateMapInfo("State: ", layer.feature.properties.name, "Population: ", layer.feature.properties.population, "Number of Districts: ", layer.feature.properties.numDistricts, "", "", "", "", "", "", "", "", "", "")
    const stateDensity=layer.feature.properties.density
    const statePopulation = layer.feature.population
    const stateNumDistricts = layer.feature.properties.numDistricts
    this.setState({stateDensity: stateDensity, statePopulation: statePopulation, stateNumDistricts:stateNumDistricts});
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
    // console.log("STATE INFO", e)
    this.updateMapInfo("State: ", e.properties.name, "Population: ", e.properties.population, "Number of Districts: ", e.properties.numDistricts, "", "", "", "", "", "", "", "", "", "")
    const stateLat = e.properties.lat
    const stateLng = e.properties.lng
    this.stateLayerControlToggle(stateName)
    this.props.onStateSelect(stateName);
    this.setState({originalState: "False", zoom: 7, stateSelected: true, lat:stateLat, lng:stateLng});
    }

    dropdownStateSelect(ev, stateNum){
      var geoJSON = this.state.states
      var e = geoJSON[stateNum]
      this.clickSelectState(null, e)
    }

    updateMapInfo(label1, content1, label2, content2, label3, content3, label4, content4, label5, content5, label6, content6, label7, content7, label8, content8){
      var mapInfo = this.state.mapInfo
      mapInfo['text1'] = label1.concat(content1)
      mapInfo['text2'] = label2.concat(content2)
      mapInfo['text3'] = label3.concat(content3)
      mapInfo['text4'] = label4.concat(content4)
      mapInfo['text5'] = label5.concat(content5)
      mapInfo['text6'] = label6.concat(content6)
      mapInfo['text7'] = label7.concat(content7)
      mapInfo['text8'] = label8.concat(content8)
      this.setState({mapInfo: mapInfo})
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

    var precinct = e.target.feature.properties
    var demData = precinct.demographicData
    var black = Math.round(demData.black)
    var whiteNonHispanic = Math.round(demData.whiteNonHispanic)
    var hispanic = Math.round(demData.asian)
    var asian = Math.round(demData.black)
    var americanIndian = Math.round(demData.americanIndian)
    var pacific = Math.round(demData.pacific)
    var twoOrMoreRaces = Math.round(demData.twoOrMoreRaces)
    this.updateMapInfo(
    "Black or African American", black,
    "Non-Hispanic White: ", whiteNonHispanic,
    "Hispanic/Latino: ",hispanic,
    "Asian: ",asian,
    "Native Americans & Alaska Natives ", americanIndian,
    "Native Hawaiians and Other Pacific Islanders: ",pacific,
    "Two or more races:",twoOrMoreRaces,
  "", "")
    layer.setStyle(this.state.precinctHighlightStyle);
  }

  resetPrecinct = (component, e) => {
    var layer = e.target;
    layer.setStyle(this.state.precinctDefaultStyle)
  }

  getPrecinctColor = (component, feature, layer) => {
    if(this.state.heatMapOn){
      if(this.state.heatMapOption == 'original'){
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
      }else{ //HEAT MAP OPTION
        const totalPop = feature.properties.demographicData.totalPop
        const targetDem = this.props.currDem
        var demData = feature.properties.demographicData
        var targetPop = this.getTargetDemographicPopulation(targetDem, demData)
        const precinctPercent = (targetPop/totalPop)*100
        const stateAverage = this.state.stateAverage[this.props.currState][targetDem]
        const d = precinctPercent - stateAverage
        // console.log("state avg:", stateAverage, "precinct: ", precinctPercent, "d: ", d)
        var color = d > 20 ? '#751010' :
           d > 15  ? '#a61f1f' :
           d > 10  ? '#cf3c3c' :
           d > 5  ? '#e06363' :
           d > 0  ? '#ffffff' :
           d > -5  ? '#e0fbff' :
           d > -10  ? '#c9f8ff' :
           d > -15  ? '#9ad1d9' :
           d > -20  ? '#7db5bd' :
                    '#4a1024';
        return {
          weight: 2,
          color: color,
          dashArray: '',
          fillColor: color,
          fillOpacity: 1.0,
        }
      }
    }else if(this.props.districtingOn){
      // const d = feature.properties.random.districtID -- something like this
      // const d = feature.properties.districtID
      // var color = d > 15 ? '#cbc7d4' :
      //     d > 14  ? '#08ff8b' :
      //     d > 13  ? '#5df5ae' :
      //     d > 12  ? '#1de085' :
      //     d > 11  ? '#00c468' :
      //     d > 10  ? '#0d7343' :
      //     d > 9  ? '#54876f' :
      //    d > 8  ? '#1b4332' :
      //    d > 7  ? '#2d6a4f' :
      //    d > 6  ? '#40916c' :
      //    d > 5  ? '#52b788' :
      //    d > 4  ? '#74c69d' :
      //    d > 3  ? '#95d5b2' :
      //    d > 2  ? '#b7e4c7' :
      //    d > 1  ? '#d8f3dc' :
      //             '#d8f3dc';
      // return {
      //   weight: 2,
      //   color: color,
      //   dashArray: '',
      //   fillColor: color,
      //   fillOpacity: 1.0,
      // }
      return this.state.precinctDefaultStyle
    }else{
      return this.state.precinctDefaultStyle
    }
  }

  getDistricting(){
    // console.log("getDistricting")
    // console.log("NC_RANDOM", NC_random)
    // console.log("precinct", arkansasPrecincts)
    if(this.props.currState == 'Virginia'){
      console.log("VIRGINIA!")
      // this.setState({currentDist: [VA_random, VA_average, VA_random, VA_random]})
    }
    // var mergedDistrict = geojsonMerge.merge(sample_districting);
    // var precincts = sample_districting
    // var poly1 = turf.polygon(precincts[0]["geometry"]["coordinates"], {"fill": "#0f0"});
    // var poly2 = turf.polygon(precincts[1]["geometry"]["coordinates"], {"fill": "#00f"});
    // var union = turf.union(poly1, poly2);
    // var poly1, poly2, union
    // for(var i=2; i< precincts.length; i++){
    //   poly1 = turf.polygon(precincts[i]["geometry"]["coordinates"], {"fill": "#0f0"});
    //   poly2 = turf.polygon(precincts[i+1]["geometry"]["coordinates"], {"fill": "#00f"});
    //   union = turf.union(poly1, poly2, union);
    // }
    //

    // console.log("New Union performed ", mergedDistrict)

    // GET REQUESTS FOR DISTRICTINGS HERE
  }

  getTargetDemographicPopulation(targetDem, targetPop){
    switch(targetDem){
      case 'black':
        return targetPop.black
        break;
      case 'whiteNonHispanic':
        return targetPop.whiteNonHispanic
        break;
      case 'hispanic':
        return targetPop.americanIndian
        break;
      case 'asian':
        return targetPop.asian
        break;
      case 'americanIndian':
        return targetPop.americanIndian
        break;
      case 'pacific':
        return targetPop.pacific
        break;
      case 'twoOrMoreRaces':
        return targetPop.twoOrMoreRaces
        break;
    }
  }

  resetMap(){
    for(var i=0; i<3; i++){
      layerControl[i].style.visibility = 'hidden';
    }
    this.setState(state => ({originalState: "True", stateSelected: false, lat: 37.090240, lng: -95.712891, zoom: 5, stateDensity: " "}));
    this.props.onReset();
  }

  heatMapOn(e){
    if(this.state.heatMapOn){
      this.setState({heatMapLegendStyle: {display: 'none'}, heatMapLegendStyleAverage: {display: 'none'}, heatMapMenuStyle:  {display: 'none'}, heatMapOn: false, heatMapButton: {backgroundColor: '#3B2B59', borderColor: 'grey', borderWidth: 'thick'}})
    }else{
      if(this.props.currDem != null){
        this.props.handleHeatMapSelect()
        this.districtingOff()
        this.setState({heatMapLegendStyle: {display: 'block'}, heatMapMenuStyle:  {display: 'block'}, heatMapOn: true, heatMapButton: {backgroundColor: '#3B2B59', borderColor: '#39ff14', borderWidth: 'thick'}})
      }else{
        alert("Please select target demographic for heat map")
      }
    }
  }

  heatMapOption(e, option){
    this.setState({heatMapOption: option})
    if(option=='original'){
      this.setState({
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
      }})
      this.setState({
        heatMapLegendStyle:{display: 'block'}
      })
      this.setState({
        heatMapLegendStyleAverage:{display: 'none'}
      })
    }else{
      this.setState({
        heatMapStyles: {
        0:{backgroundColor: '#7db5bd'},
        1:{backgroundColor: '#9ad1d9'},
        2:{backgroundColor: '#c9f8ff'},
        3:{backgroundColor: '#e0fbff'},
        4:{backgroundColor: '#ffffff'},
        5:{backgroundColor: '#cf3c3c'},
        6:{backgroundColor: '#a61f1f'},
        7:{backgroundColor: '#751010'},
        8:{backgroundColor: '#85001b'},
        9:{backgroundColor: '#6e0016'},
      }})
      this.setState({
        heatMapLegendStyleAverage:{display: 'block'}
      })
      this.setState({
        heatMapLegendStyle:{display: 'none'}
      })
    }
  }

  districtingOn(){
    this.setState({districtingMenuStyle: {visibility: 'visible'}})
    this.setState({districtingOn: true})
  }

  districtingOff(){
    this.props.handleDistrictingClose()
    this.setState({districtingOn: false})
  }

  handleDistrictingRadioOption(e, option){
    this.getDistricting()
    this.districtingOn()
    this.setState({selectedDistricting: option})
    var dist = this.state.checkedDist
    if (dist[option]==true){
        dist[option] = false
    }else{
      dist[option] = true
    }
    this.setState({checkedDist: dist})
    var optionDict = {"Random": 0, "Average": 1, "Extreme": 2, "Minimum": 3}
    // var distStyle = this.state.districtingStyle[optionDict[option]]
    // console.log("distStyle ", distStyle)
    // if (distStyle.visibility == 'visible'){
    //   distStyle.visibility = 'hidden'
    // }else{
    //   distStyle.visibility = 'visible'
    // }
    // this.state.districtingStyle[optionDict[option]] = distStyle
  }

  getdistrictingStyle(dist, i){
    var optionDict = {0: "Random", 1:"Average", 2:"Extreme", 3:"Minimum"}
    if(this.state.districtingOn){
      if(this.state.checkedDist[optionDict[dist]]){
        return this.state.districtingStyle[dist]
      }else{
        return this.state.defaultDistrictingStyle
      }
    }else{
      return this.state.defaultDistrictingStyle
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
    const heatMapAverageGrades = [20, 15, 10, 5, 0, -5, -10, -15, -20]
    const dropdownStates = ['Virginia', 'North Carolina', 'Arkansas']
    const camelcaseStates = ['virginia', 'northCarolina', 'arkansas']
    const dropdownDemographics = ['Black or African American', 'Non-Hispanic White', 'Hispanic and Latino', 'Asian', 'Native Americans and Alaska Natives','Native Hawaiians and Other Pacific Islanders', 'Two or more races' ]
    const districtingTypes = ['Random', 'Average', 'Extreme', 'Minimum']
    const GreenCheckbox = withStyles({
      root: {
        color: green[400],
        '&$checked': {
          color: green[600],
        },
      },
      checked: {},
    })((props) => <Checkbox color="default" {...props} />);

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
          <p className='currMap-Info'> Selected Demographic: {this.state.demographicDict[this.props.currDem]}</p>
        </div>
      <div className='leaflet-container'>
        <Map center={position} zoom={this.state.zoom} ref={mapRef} onClick={this.mapClick}>
        <div className='districtingMenu' style={this.props.districtingMenuStyle}>
          <MuiThemeProvider theme={theme}>
            <FormControl component="fieldset"><FormLabel component="legend">Districtings</FormLabel>
            <p>JobID: {this.props.districtingJobId}</p>
                <FormControlLabel
                  control={<RandomCheckbox checked={this.state.checkedDist[('Random')]} onClick={(e) => this.handleDistrictingRadioOption(e, 'Random')} name={'Random'} />}
                  label={'Random'}
                />
                <FormControlLabel
                  control={<AverageCheckbox checked={this.state.checkedDist[("Average")]} onClick={(e) => this.handleDistrictingRadioOption(e, "Average")} name={"Average"} />}
                  label={"Average"}
                />
                <FormControlLabel
                  control={<ExtremeCheckbox checked={this.state.checkedDist[("Extreme")]}  onClick={(e) => this.handleDistrictingRadioOption(e, "Extreme")} name={"Extreme"} />}
                  label={"Extreme"}
                />
                <FormControlLabel
                  control={<MinimumCheckbox checked={this.state.checkedDist[("Minimum")]}  onClick={(e) => this.handleDistrictingRadioOption(e, "Minimum")} name={"Minimum"} />}
                  label={"Minimum"}
                />
            </FormControl>
            </MuiThemeProvider>
            <Button variant="button" className="submitButton" onClick={this.districtingOff}>Close</Button>{' '}
        </div>
        <div className='heatMapMenu' style={this.state.heatMapMenuStyle}>
          <FormControl component="fieldset">
            <FormLabel component="legend">Map Options</FormLabel>
            <RadioGroup aria-label="gender" name="gender1" value={this.state.heatMapOption}>
              <FormControlLabel value="original" control={<Radio />} label="Original" onClick={(e) => this.heatMapOption(e, "original")}/>
              <FormControlLabel value="state" control={<Radio />} label="State Average" onClick={(e) => this.heatMapOption(e, "state")} />
            </RadioGroup>
          </FormControl>
        </div>
        <div className='heatMapLegend' style={this.state.heatMapLegendStyle}>
          {heatMapGrades.map((grade, i, array) =>
            <div key={i} className='text'>
              <i style={this.state.heatMapStyles[i]}></i>
              {(heatMapGrades[i+1])
                ? <p key={i}>{grade}% - {heatMapGrades[i+1]}% </p>
                : <p key={i}>{grade}% +</p>
              }
            </div>
          )}
        </div>
        <div className='heatMapLegend' style={this.state.heatMapLegendStyleAverage}>
          <p> Above/Below State Average </p>
          {heatMapAverageGrades.map((grade, i, array) =>
            <div key={i} className='text'>
              <i style={this.state.heatMapStyles[i]}></i>
              {(heatMapAverageGrades[i] > 0)
                ? <p key={i}>{grade}% above </p>
                : <p key={i}>{grade}% below</p>
              }
            </div>
          )}
        </div>
        <button className="reset-button" onClick={this.resetMap} >Reset Map</button>{' '}
        <div className='map-information'>
          {Object.entries(this.state.mapInfo).map( ([i, text]) => (
            <p key={i} className='state-info'>{text}</p>
          ))}
        </div>
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
              <LayerGroup ref={firstOverlayRef}>
              <GeoJSON
                data={this.state.precincts[stateName]}
                style={this.getPrecinctColor.bind(null, this)}
                onEachFeature={this.onEachPrecinct.bind(null, this)}
              ></GeoJSON>
            </LayerGroup>
          </Overlay>
          <Overlay name="District Borders">
              <LayerGroup ref={secondOverlayRef}>
                <GeoJSON key={"districtLayer"+stateName}
                  data={this.state.districts[stateName]}
                  style={this.state.districtDefaultStyle}
                  ></GeoJSON>
          </LayerGroup>
        </Overlay>
            </LayersControl>
        )}
            {(this.state.currentDist).map((dist, i) =>
              <GeoJSON key={i}
                data={this.state.currentDist[i]}
                style={this.getdistrictingStyle.bind(dist, i)}
                ></GeoJSON>
          )}
        </Map>
      </div>
    </div>
    )
  }
}
