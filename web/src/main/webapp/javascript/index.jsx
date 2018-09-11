import ReactDOM from 'react-dom';
import React from 'react';
import App from './app'
import '../css/general.css';

if (document.getElementById('game_terminal') != null)  {
  ReactDOM.render(
    <div>
      <App />
    </div>,
    document.getElementById('game_terminal')
  );
}
