import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '80%',
    marginLeft:'5%',
  },
  margin: {
    height: theme.spacing(3),
  },
}));

const marks = [
  {
    value: 60,
    label: 'Least',
  },
  {
    value: 65,
    label: 'Less',
  },
  {
    value: 70,
    label: 'Average',
  },
  {
    value: 75,
    label: 'Very',
  },
  {
    value: 80,
    label: 'Extremely',
  },
];



const CompactnessSlider= ({onNewNumber}) => {
  const [value, setValue] = React.useState(70);
  const classes = useStyles();

  const valuetext = (value) => {
    return `${value}`;
  }

  const handleChange = (event, newValue) => {
    onNewNumber(newValue);
  }

  return (
    <div className={classes.root}>
      <Slider
        defaultValue={70}
        aria-labelledby="discrete-slider-custom"
        step={5}
        min={60}
        max={80}
        valueLabelDisplay="auto"
        marks={marks}
        onChange={handleChange}

      />
    </div>
  );
}

export default CompactnessSlider;
