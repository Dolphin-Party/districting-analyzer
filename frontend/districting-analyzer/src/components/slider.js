import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';
import Input from '@material-ui/core/Input';

const useStyles = makeStyles({
  root: {
    width: '90%',
  },
  input: {
    width: '60px',
  },
});

const InputSlider = ({onNewNumber, data}) => {
  const classes = useStyles();
  // const [value, setValue] = React.useState(100);
  // const {min1, max1} = React.useState(0, 100);
  const [value, setValue] = React.useState(data.min);
  const {min1, max1} = React.useState(data.min, data.max);
  const handleSliderChange = (event, newValue) => {
    setValue(newValue);
    onNewNumber(newValue);
  };
  const handleInputChange = (event) => {
    setValue(event.target.value === '' ? '' : Number(event.target.value));
  };
  const handleBlur = () => {
    if (value < 0) {
      setValue(0);
    } else if (value > 5000) {
      setValue(5000);
    }
  };


  return (
    <div className={classes.root}>
      <Typography id="input-slider" gutterBottom>
      </Typography>
      <Grid container spacing={2} alignItems="center">
        <Grid item>
        </Grid>
        <Grid item xs>
          <Slider
            value={typeof value === 'number' ? value : 0}
            onChange={handleSliderChange}
            aria-labelledby="input-slider"
            min={data.min}
            max={data.max}
          />
        </Grid>
        <Grid item>
          <Input
            className={classes.input}
            value={value}
            margin="dense"
            onChange={handleInputChange}
            onBlur={handleBlur}
            inputProps={{
              step: 100,
              min: {min1},
              max: {max1},
              type: 'number',
              'aria-labelledby': 'input-slider',
            }}
          />
        </Grid>
      </Grid>
    </div>
  );
}

export default InputSlider;
