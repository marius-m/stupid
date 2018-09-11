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
    console.log("Rerendering state " + this.state.uuid);
    if (this.state.uuid !== '' && this.state.source !== '') {
      return(
        <CodeResult uuid={this.state.uuid} source={this.state.source} />  
      );
    }
    return (
      <CodeSubmit doSearch={(uuid, source) => this.setState({ uuid: uuid, source: source })} />
    );
  }
}
