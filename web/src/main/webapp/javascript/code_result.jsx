import React, {Component} from "react";
import { Observable } from 'rxjs';
import { timer } from 'rxjs/observable/timer';
import { fromPromise } from 'rxjs/observable/fromPromise';
import { map, takeWhile, flatMap, filter, take } from 'rxjs/operators';
import $ from 'jquery'; 

export default class CodeResult extends Component {

  constructor(props) {
    super(props);
    this.state = {
      uuid: this.props.uuid,
      source: this.props.source,
      output: ''
    };
  }

  componentDidMount() {
    console.log("Fetching events for " + this.state.uuid + " / " + this.state.source);
    timer(0, 3000)
      .pipe(flatMap(timerItem => {
         return fromPromise($.getJSON('http://localhost:8080/api/code_result?uuid=' + this.state.uuid));
      }))
      .pipe(filter(response => { return response.status == 200 }))
      .pipe(take(1))
      .subscribe(jsonResponse => {
        this.setState({
          uuid: this.props.uuid,
          source: this.props.source,
          output: jsonResponse.output.result
        });
      })
  }

  render() {
    if (this.state.output === '') {
       return(<div>Loading...</div>);
    }
    return (
      <div>Output: {this.state.output}</div>
    );
  }
}
