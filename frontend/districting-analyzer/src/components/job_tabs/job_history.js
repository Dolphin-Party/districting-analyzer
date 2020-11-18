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
import Table from 'react-bootstrap/Table'


export default class JobHistoryTab extends Component<{}, State> {
  constructor(props) {
      super(props);

      const jobs = [
      {num:'1', state:'North Carolina', status: 'Done', numDist: '5000', comp: 'Average', dem:'Black or African American', pvap: '20%'},
      {num:'2', state:'Virginia', status: 'Processing', numDist: '3000', comp: 'Very Compact', dem:'Black or African American', pvap: '30%'}];

      // for (let i = 0; i < 10; i++) {
      //     people.push({
      //         name: chance.first(),
      //         country: chance.country({ full: true })
      //     });
      // }

      this.state = { jobs };
  }

    jobButton = (status) => {
      if(this.status == 'In Queue' | this.status=='Processing'){
        return 'Cancel Job'
      }else if(this.status == 'Done'){
        return 'Delete Job'
      }
    }

    render() {
    return (
      <div className='jobs-list'>
        {this.state.jobs.map((job, index) => (
          <div key={index} className='job-info'>
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Job #</th>
                  <th>{job.num}</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>State:</td>
                  <td>{job.state}</td>
                </tr>
                <tr>
                  <td>Status</td>
                  <td>{job.status}</td>
                </tr>
                <tr>
                  <td>Number of Districtings:</td>
                  <td>{job.numDist}</td>
                </tr>
                <tr>
                  <td>% of Compactness:</td>
                  <td> {job.comp}</td>
                </tr>
                <tr>
                  <td>Racial/Ethnic Demographic:</td>
                  <td> {job.dem}</td>
                </tr>
                <tr>
                  <td>% of voting age population:</td>
                  <td> {job.pvap}</td>
                </tr>
                <tr>
                  <td><Button variant="button" className="button">Delete Job</Button>{' '}</td>
                  <td> <Button variant="button" className="button">Show Data Plot</Button>{' '}</td>
                </tr>
              </tbody>
            </Table>
          </div>
        ))}
      </div>
    )
  }
}
