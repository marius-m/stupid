import React, {Component} from "react";

export default class PlayerDetails extends Component {

  constructor(props) {
    super(props);
    this.state = {
      game_id: this.props.game_id,
      player_id: this.props.player_id, 
      player: null
    };
  }

  componentDidMount() {
    fetch("http://localhost:8080/api/game/player/"+this.state.game_id+"/"+this.state.player_id, { method: 'GET' })
    .then((response) => response.json())
    .then((responseJson) => {
        var player = new Player(responseJson.name, responseJson.cardDisplay, responseJson.cards)
        this.setState({
            game_id: this.state.game_id,
            player_id: this.state.player_id, 
            player: player
        })
    }).catch((error) => {
      console.error(error);
    });
  }

  render() {
      if (this.state.player == null) {
        return(
            <div>No player info</div>
        );
      } else {
        var cardsAsDisplay = this.state.player.cards
            .map((card) => card.display)
            .join();
        return(
            <div>
                <p>Player details</p>
                <ul>
                    <li>Game id: {this.state.game_id}</li>
                    <li>Player id: {this.state.player_id}</li>
                    <li>Player: {this.state.player.name} / {cardsAsDisplay}</li>
                </ul>
            </div>
        );
      }
  }
}

class Card {
    constructor(suite, rank, display) {
        this.suite = suite;
        this.rank = rank;
        this.display = display;
    }
}

class Player {
    constructor(name, cardDisplay, cards) {
        this.name = name;
        this.cardDisplay = cardDisplay;
        this.cards = cards.map(card => new Card(card.suite, card.rank, card.display));
    }
}
