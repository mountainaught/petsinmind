import React from 'react';
import "./css/reset.css";
import "./css/login.css";

export default function login() {
    return (
        <div className="login-container">
            <button className='loginBtn'>Login</button>
            <button className='registerBtn'>Register</button>
        </div>
    );
}

