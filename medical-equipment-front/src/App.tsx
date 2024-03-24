import React from 'react';
import './App.css';
import { Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage';
import NotFoundPage from './pages/404Page';
import LoginPage from './pages/LoginPage';
import AppNavbar from './components/AppNavbar';
import SignupPage from './pages/SignUpPage';
import ProfilePage from './pages/ProfilePage';
import CompaniesListPage from './pages/CompaniesListPage';
import ComplaintPage from './pages/ComplaintPage';
import AdminComplaintPage from './pages/admin/AdminComplaintPage';
import RateCompaniesPage from './pages/RateCompaniesPage';

function App() {
  return (
      <>
      <AppNavbar />
        <Routes>
          <Route path="/admin/complaint" element={<AdminComplaintPage />} />
          <Route path="/complaint" element={<ComplaintPage />} />
          <Route path="/companies/rate" element={<RateCompaniesPage />} />
          <Route path="/companies" element={<CompaniesListPage />} />
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
