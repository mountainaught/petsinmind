import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import logo from './assets/Logo.png';

export default function Layout() {
    const location = useLocation();
    const navigate = useNavigate();

    // So that we can show/hide nav buttons depending on page
    const isLoginPage = location.pathname === '/login' || location.pathname === '/register' || location.pathname === '/details';
    const notification = false; // Placeholder for notification logic

    const isChat = location.pathname === '/chat';

    return (
        <div>
            <nav className='navbar'>
                <div className='logoandtitle'>
                    <img src={logo} alt="Logo" />
                    <h1>Pets In Mind</h1>
                </div>

            {!isLoginPage && (
                    <div className='navbar-buttons'>
                        <button onClick={() => navigate('/login')}>Login</button>
                        <p>|</p>
                        <button onClick={() => navigate('/')}>Home</button>
                        <p>|</p>
                        <button>Help</button>
                    </div>
            )}

            {isLoginPage && (
            <div className='navbar-buttons'>
            <button onClick={() => navigate('/petownerhome')}>Home</button>
            <p>|</p>
            <button>Help</button>
          </div>
        )}
      </nav>

           

            {!isChat && (
                <button className="chatBtn" onClick={() => navigate('/chat')}>
                    <img
                        className="chatIcon"
                        src={notification ? "./src/assets/Chat_Icon_Notification.png" : "./src/assets/Chat_Icon.png"}
                        alt="Chat"
                    />
                </button>
            )}

        {/* Background image */}
        <img className="bgImage" src="/src/assets/paws.png" alt="Paws" />

        <Outlet />
        </div>
    );
}
