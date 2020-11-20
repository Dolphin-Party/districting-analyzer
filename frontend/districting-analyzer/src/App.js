import React from 'react';
import Nav from './components/navigation'
import {BrowserRouter as Router, Switch, Route, Redirect} from 'react-router-dom'
import PageRenderer from './page-renderer'
// const React = require('react');
// const ReactDOM = require('react-dom');
// const client = require('./client');

function App() {
  return (
    <Router>
        <div className="App">
          <Nav/>
          <Switch>
            <Route path="/:page" component={PageRenderer} />
            <Route path="/" render={()=> <Redirect to="/home" />} />
            <Route component={() => 404}/>
          </Switch>
        </div>
    </Router>
  );
}

export default App;
