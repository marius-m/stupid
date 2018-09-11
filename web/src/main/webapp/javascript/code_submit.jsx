import React, {Component} from "react";

export default class CodeSubmit extends Component {

  constructor(props) {
    super(props);
    this.state = {
      uuid: '',
      source: '',
      output: '', 
      error: ''
    };
    this.handleChange = this
      .handleChange
      .bind(this);
    this.handleSubmit = this
      .handleSubmit
      .bind(this);
  }

  componentDidMount() {
    fetch('http://localhost:8080/api/code_issue_uuid', { method: 'GET' })
    .then((response) => response.json())
    .then((responseJson) => {
      this.setState({
        source: this.state.source,
        uuid: responseJson.uuid, 
        output: this.state.output, 
        error: ''
      })
    }).catch((error) => {
      console.error(error);
    });
  }

  handleChange(event) {
    this.setState({
      source: event.target.value, 
      uuid: this.state.uuid
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    fetch('http://localhost:8080/api/code_submit', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        uuid: this.state.uuid,
        source: this.state.source
      }),
    })
    .then(response => {
      if (response.status == 200) {
        this.props.doSearch(this.state.uuid, this.state.source) 
      } else {
        this.setState({
          source: this.state.source,
          uuid: this.state.uuid, 
          output: this.state.output, 
          error: 'Error submitting code for execution. Try again later.'
        })
      }
    })
    .catch(error => {
        this.setState({
          source: this.state.source,
          uuid: this.state.uuid, 
          output: this.state.output, 
          error: 'Error submitting code for execution. Try again later'
        })
    })
  }

  render() {
    const isError = this.state.error !== '';
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <label>Code to execute</label><br/>
          <textarea
            type="java_code"
            name="source"
            value={this.state.source}
            onChange={this.handleChange}/><br/>
          <input type="submit" value="Submit"/><br/>
        </form>
        <b>{isError ? this.state.error : ''}</b>
      </div>
    );
  }
}
