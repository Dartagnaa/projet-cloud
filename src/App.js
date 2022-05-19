import logo from './logo.svg';
import './App.css';
import React from 'react';

import 'antd/dist/antd.css';
import { Button } from 'antd';

class App extends React.Component {

  GetTest() {
    // Simple GET request using fetch
    fetch('https://api.npms.io/v2/search?q=react')
        .then(response => response.json());
  }

  render() {
    return  (
      <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <Button type="primary" onClick={this.GetTest}>GET</Button>
      </header>
    </div>
    );    
  }
}

export default App;
