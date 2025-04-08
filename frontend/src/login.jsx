import React from 'react';
import "./css/reset.css";
import "./css/login.css";
import "./css/mobile.css";
import { useNavigate } from 'react-router-dom';

export default function login() {
    const navigate = useNavigate();

    return (
        <div className="login-container">
            <button className='loginBtn' onClick={() => navigate('/details')}>Log In</button>
            <button className='registerBtn' onClick={() => navigate('/register')}>Register</button>
        </div>
    );
}

