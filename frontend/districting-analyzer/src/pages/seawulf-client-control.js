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


export default class SeawulfClientControl extends Component<{}, State> {
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
      <div>
      <div className='seawulf-client-control'>
        <p className='title'>Generating Districtings</p>
          <Button variant="slider-button" className="slider-button"># of Districtings
          <RangeSlider
              value={value}
            />
          </Button>{' '}
          <Button variant="slider-button" className="slider-button">% of Compactness
          <RangeSlider
              value={value}
            />
          </Button>{' '}
          <Button variant="slider-button" className="slider-button"># of Random Districting Plans
          <RangeSlider
              value={value}
            />
          </Button>{' '}
          <Button variant="slider-button" className="slider-button">% of BVAP
          <RangeSlider
              value={value}
            />
          </Button>{' '}
        <Button variant="slider-button" className="slider-button">Generate Randomized Districting Comparison
        <RangeSlider
            value={value}
          />
        </Button>{' '}
        <Button variant="button" className="button">Generate Districting Comparisons</Button>{' '}

      </div>
        </div>
      )
    }
  }
