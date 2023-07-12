import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import './index.css';
import LoginPage from "./pages/login/LoginPage";
import NotFound from "./pages/notfound/NotFound";
import MainPage from "./pages/main/MainPage";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/admin" element={<MainPage/>}/>
            <Route path="/admin/login" element={<LoginPage/>}/>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    </BrowserRouter>
);
