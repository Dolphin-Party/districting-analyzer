import React, { Component } from 'react';
import CanvasJSReact from '../canvasjs-3.2/canvasjs.react'
var CanvasJS = CanvasJSReact.CanvasJS;
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

class BoxWhiskerPlot extends Component {
	constructor(props) {
    super(props);
    this.state = {
			jobId: this.props.boxWhiskerData.jobId,
			stateName: this.props.boxWhiskerData.state,
			dataPoints: this.props.boxWhiskerData.dataPoints,
    };
		console.log(this.props.boxWhiskerData.jobId);
  }


	render() {
		const options = {
			theme: "light2",
			animationEnabled: false,
			height:300,
			title:{
				text: "Job #" + this.props.boxWhiskerData.jobId + " | State: " + this.props.boxWhiskerData.state + " | Black or African American"
			},
			axisY: {
				title: "Target Demographic VAP%"
			},
			data: [{
				type: "boxAndWhisker",
				yValueFormatString: ""%"",
				dataPoints: this.props.boxWhiskerData.dataPoints,
			},
			{
				type: "scatter",
				markerSize: 15,
				toolTipContent: "District: {x+1} Population: {y}",
				dataPoints: this.props.enactedPlan,
			}

		],
		}
		return (
		<div>
			<CanvasJSChart options = {options}
				/* onRef={ref => this.chart = ref} */
			/>
			{/*You can get reference to the chart instance as shown above using onRef. This allows you to access all chart properties and methods*/}
		</div>
		);
	}
}

export default BoxWhiskerPlot;
