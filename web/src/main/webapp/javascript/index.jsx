import ReactDOM from 'react-dom';
import React from 'react';
import App from './app'
import '../css/general.css';

if (document.getElementById('game_terminal') != null)  {
  var gameId = document.getElementById('game_id').value
  var playerId = document.getElementById('player_id').value
  ReactDOM.render(
    <div>
      <App game_id={gameId} player_id={playerId} />
    </div>,
    document.getElementById('game_terminal')
  );
}
