import React, {Component} from "react";

export default class PlayerActions extends Component {

  constructor(props) {
    super(props);
    this.state = {
      game_id: this.props.game_id,
      player_id: this.props.player_id, 
      actions: []
    };
    this.handleAction = this.handleAction.bind(this);
  }

  componentDidMount() {
    fetch("/api/game/actions/"+this.state.game_id+"/"+this.state.player_id, { method: 'GET' })
    .then((response) => response.json())
    .then((responseJson) => {
        const actions = responseJson.actions
            .map((action) => new Action(action.description, action.trigger));
        this.setState({
            game_id: this.state.game_id,
            player_id: this.state.player_id, 
            actions: actions
        })
    }).catch((error) => {
      console.error(error);
    });
  }

  handleAction(e, action) {
    e.preventDefault();
    console.log("Triggering action " + action.trigger);
    const actionRequest = new RequestAction(action.trigger);
    fetch(
        "/api/triggerAction/player/"+this.state.game_id+"/"+this.state.player_id,
        {
            method: 'POST' , 
            body: JSON.stringify(actionRequest), 
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'                  
            },
        })
        .then((response) => {
            console.log("Response: " + response.status)
        }).catch((error) => {
            console.error(error);
        });
  }

  render() {
      if (this.state.actions.length <= 0) {
        return(
            <div>No actions available</div>
        );
      } else {
        const actionsAsJsx = this.state.actions
            .map((action) => 
                <a className="btn btn-primary btn-lg" role="button"
                    onClick={(e) => this.handleAction(e, action)}>{action.description}</a>
            );
        return(
            <div>
                <p>Player actions</p>
                <div>
                    {actionsAsJsx}
                </div>
            </div>
        );
      }
  }
}

export class Action {
    constructor(description, trigger) {
        this.description = description;
        this.trigger = trigger;
    }
}

class RequestAction {
    constructor(actionAsString) {
        this.actionAsString = actionAsString;
    }
}