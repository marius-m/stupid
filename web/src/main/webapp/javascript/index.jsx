import ReactDOM from 'react-dom';
import React from 'react';
import App from './app'
import '../css/general.css';

if (document.getElementById('code_submit') !=null)  {
  ReactDOM.render(
    <div>
      <App />
    </div>,
    document.getElementById('code_submit')
  );
}
