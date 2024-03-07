import React from 'react';
import './App.css';
import { Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage';
import NotFoundPage from './pages/404Page';
import LoginPage from './pages/LoginPage';
import AppNavbar from './components/AppNavbar';
import SignupPage from './pages/SignUpPage';
import ProfilePage from './pages/ProfilePage';

function App() {
  return (
      <>
      <AppNavbar />
        <Routes>
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/" element={<HomePage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </>  
  );
}

export default App;
