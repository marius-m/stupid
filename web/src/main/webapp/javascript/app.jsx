import React, {Component} from "react";
import PlayerDetails from "player_details";
import PlayerActions from "player_actions";

export default class App extends Component {
  // Props: 
  // game_id; player_id
  constructor(props) {
    super(props);
    this.state = {
      game_id: this.props.game_id,
      player_id: this.props.player_id
    };
  }

  render() {
    if (this.state.game_id == null || this.state.player_id == null) {
      return(
        <div>
          Error loading
        </div>
      );
    }
    return (
      <div>
        <h1>Game status</h1>
        <PlayerDetails game_id={this.state.game_id} player_id={this.state.player_id} />
        <PlayerActions game_id={this.state.game_id} player_id={this.state.player_id} />
      </div>
    );
  }
}
