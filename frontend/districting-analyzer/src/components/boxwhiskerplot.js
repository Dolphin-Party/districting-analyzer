import React, { Component } from 'react';
import CanvasJSReact from '../canvasjs-3.2/canvasjs.react'
var CanvasJS = CanvasJSReact.CanvasJS;
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

class BoxWhiskerPlot extends Component {
	render() {
		const options = {
			theme: "light2",
			animationEnabled: false,
			width:1000,
			height:300,
			title:{
				text: "Virginia Current Districting"
			},
			axisY: {
				title: "Target Demographic VAP%"
			},
			data: [{
				type: "boxAndWhisker",
				yValueFormatString: ""%"",
				dataPoints: [
					{ label: "1",  y: [179, 256, 300, 418, 274] },
					{ label: "2",  y: [252, 346, 409, 437, 374.5] },
					{ label: "3",  y: [236, 281.5, 336.5, 428, 313] },
					{ label: "4",  y: [340, 382, 430, 452, 417] },
					{ label: "5",  y: [194, 224.5, 342, 384, 251] },
					{ label: "6",  y: [241, 255, 276.5, 294, 274.5] },
					{ label: "7",  y: [340, 382, 430, 452, 417] },
					{ label: "8",  y: [194, 224.5, 342, 384, 251] },
					{ label: "9",  y: [241, 255, 276.5, 294, 274.5] }
				]
			}]
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
