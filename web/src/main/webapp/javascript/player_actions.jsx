import React, {Component} from "react";

export default class PlayerActions extends Component {

  constructor(props) {
    super(props);
    this.state = {
      game_id: this.props.game_id,
      player_id: this.props.player_id, 
      actions: []
    };
  }

  componentDidMount() {
    fetch("http://localhost:8080/api/game/actions/"+this.state.game_id+"/"+this.state.player_id, { method: 'GET' })
    .then((response) => response.json())
    .then((responseJson) => {
        const actions = responseJson.actions
            .map((action) => new Action(action.description));
        this.setState({
            game_id: this.state.game_id,
            player_id: this.state.player_id, 
            actions: actions
        })
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
                    href="#">{action.description}</a>
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