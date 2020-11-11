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

function valuetext(value) {
  return `${value}`;
}

export default function CompactnessSlider() {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Slider
        defaultValue={50}
        getAriaValueText={valuetext}
        aria-labelledby="discrete-slider-custom"
        step={25}
        valueLabelDisplay="auto"
        marks={marks}
      />
    </div>
  );
}
