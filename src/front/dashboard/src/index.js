import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';
import './index.css';
import Auth from './jsx/Auth';
import App from './jsx/App';
import Account from './jsx/Account';
import reportWebVitals from './reportWebVitals';
import Register from './jsx/Register';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Navigate to="/index.html" />} />
      <Route path='/index.html' element={<App />} />
      <Route path='/register.html' element={<Register />} />
      <Route path='/login.html' element={<Auth />} />
      <Route path='/account.html' element={<Account />} />
    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
