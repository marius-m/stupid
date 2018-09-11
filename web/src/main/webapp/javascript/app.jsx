import React, {Component} from "react";
import CodeSubmit from "./code_submit";
import CodeResult from "./code_result";

export default class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      uuid: '', 
      source: ''
    };
  }

  render() {
    return (
      <div>
        <h1>Hello world</h1>
        <h3>Varom pirkti valgyti</h3>
      </div>
    );
  }
}
