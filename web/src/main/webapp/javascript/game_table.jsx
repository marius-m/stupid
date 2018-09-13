import React, {Component} from "react";
import {Card} from "player_details";

export default class GameTable extends Component {

  constructor(props) {
    super(props);
    this.state = {
      game_id: this.props.game_id,
      player_id: this.props.player_id, 
      game_table: null
    };
  }

  componentDidMount() {
    fetch("/api/game/table/"+this.state.game_id, { method: 'GET' })
    .then((response) => response.json())
    .then((responseJson) => {
        const attackingCardsAsCards = responseJson.attackingCards.map((card) => new Card(card.suite, card.rank, card.display));
        const defendingCardsAsCards = responseJson.defendingCards.map((card) => new Card(card.suite, card.rank, card.display));
        const table = new Table(
            attackingCardsAsCards, 
            responseJson.attackingCardsDisplayInline, 
            defendingCardsAsCards, 
            responseJson.defendingCardsDisplayInline
        );
        this.setState({
            game_id: this.state.game_id,
            player_id: this.state.player_id, 
            game_table: table
        })
    }).catch((error) => {
      console.error(error);
    });
  }

  render() {
      if (this.state.game_table == null) {
        return(
            <div>Cannot render game table</div>
        );
      } else {
        return(
            <div>
                <p>Attacking cards</p>
                <div className="textWithBreaksDiv">
                    {this.state.game_table.attackingCardsDisplayInline}
                </div>
                <p>Defending cards</p>
                <div className="textWithBreaksDiv">
                    {this.state.game_table.defendingCardsDisplayInline}
                </div>
            </div>
        );
      }
  }
}

export class Table {
    constructor(
        attackingCards,
        attackingCardsDisplayInline,
        defendingCards,
        defendingCardsDisplayInline
    ) {
        this.attackingCards = attackingCards;
        this.attackingCardsDisplayInline = attackingCardsDisplayInline;
        this.defendingCards = defendingCards;
        this.defendingCardsDisplayInline = defendingCardsDisplayInline;
    }
}