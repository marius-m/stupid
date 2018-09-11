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
    console.log("Rendering jsx");
    return (
      <div>Hello world from jsx</div>
    );
  }
}
