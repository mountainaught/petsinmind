import React, { useState } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/mobile.css';

import logo from './assets/Logo.png';

export default function Layout() {

    

    const location = useLocation();
    const navigate = useNavigate();

    const [showHelp, setShowHelp] = useState(false);
    const [showTicketRequest, setShowTicketRequest] = useState(false);
 
    // So that we can show/hide nav buttons depending on page
    const isLoginPage = location.pathname === '/login' || location.pathname === '/register' || location.pathname === '/details';
    const notification = false; // Placeholder for notification logic

    const isChat = location.pathname === '/chat';



    return (
        <div>
            <nav className='navbar'>
                <div className='logoandtitle'>
                    <img onClick={() => navigate('/home')} src={logo} alt="Logo" />
                    <h1>Pets In Mind</h1>
                </div>

                {!isLoginPage && (
                        <div className='navbar-buttons'>
                            <button onClick={() => navigate('/login')}>Login</button>
                            <p>|</p>
                            <button onClick={() => navigate('/petownerhome')}>Home</button>
                            <p>|</p>
                            
                            <button onClick={() => setShowHelp(!showHelp)}>Help</button>
                        </div>
                )}

                {isLoginPage && (
                    <div className='navbar-buttons'>
                        <button onClick={() => navigate('/petownerhome')}>Home</button>
                        <p>|</p>
                        <button onClick={() => setShowHelp(!showHelp)}>Help</button>
                        <button onClick={() => navigate('/caretakerHome')}>TempCT</button>
                    </div>
                )}
            </nav>

            {showHelp && (
                <div className="help">
                    <button onClick={() => setShowTicketRequest(!showTicketRequest)} className='tktReq'>Request Ticket</button>
                    {showTicketRequest && (
                        <form className='help2'
                            onSubmit={(e) => {
                                e.preventDefault();
                                const ticket = e.target.elements.ticket.value;
                                console.log(ticket); // Handle the ticket submission here

                                setShowHelp(!showHelp); setShowTicketRequest(!showTicketRequest);
                            }}
                            
                        
                        
                        >
                            <input name='title' type="text" placeholder='Title:' className='ticketInput'/>
                            <input name='ticket' placeholder='Enter Ticket / Problem faced here:' className='ticketInput' required></input>
                            <button type='submit' className='submitTkt'>Submit</button>
                        </form>

                     )}
                    <button className='closeBtn' onClick={() => {setShowHelp(false); setShowTicketRequest(false)}}>Close</button>
                </div>
            )}



            {!isChat && (
                <button className="chatBtn" onClick={() => navigate('/chat')}>
                    <img
                        className="chatIcon"
                        src={notification ? "./src/assets/Chat_Icon_Notification.png" : "./src/assets/Chat_Icon.png"}
                        alt="Chat"
                    />
                </button>
            )}

            <Outlet />
        </div>
    );
}
