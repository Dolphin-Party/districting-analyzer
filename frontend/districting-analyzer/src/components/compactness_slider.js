import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';

const useStyles = makeStyles((theme) => ({
  root: {
    width: 300,
  },
  margin: {
    height: theme.spacing(3),
  },
}));

const marks = [
  {
    value: 0,
    label: 'Least',
  },
  {
    value: 25,
    label: 'Less',
  },
  {
    value: 50,
    label: 'Average',
  },
  {
    value: 75,
    label: 'Very',
  },
  {
    value: 100,
    label: 'Extremely',
  },
];



const CompactnessSlider= ({onNewNumber}) => {
  const [value, setValue] = React.useState(50);
  const classes = useStyles();

  const valuetext = (value) => {
    console.log('this', `${value}`)
    return `${value}`;
  }

  const handleChange = (event, newValue) => {
    onNewNumber(newValue);
  }

  return (
    <div className={classes.root}>
      <Slider
        defaultValue={50}
        aria-labelledby="discrete-slider-custom"
        step={25}
        valueLabelDisplay="auto"
        marks={marks}
        onChange={handleChange}

      />
    </div>
  );
}

export default CompactnessSlider;
