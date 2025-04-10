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

    const handleHomeClick = () => {
            const homepage = localStorage.getItem('homepage');
            if (homepage) {
                navigate(JSON.parse(homepage)); // Parse the stored homepage and navigate
            } else {
                navigate('/home'); // Default to '/home' if no homepage is set
            }
        };

    return (
        <div>
            <nav className='navbar'>
                <div className='logoandtitle'>
                    <img onClick={() => navigate('/home')} src={logo} alt="Logo" />
                    <h1>Pets In Mind</h1>
                </div>

                {!isLoginPage && (
                        <div className='navbar-buttons'>
                            {localStorage.getItem('homepage') ? (
                                <button onClick={() => {
                                    localStorage.removeItem('homepage');
                                    navigate('/login');
                                }}>Log Out</button>
                            ) : (
                                <button onClick={() => navigate('/login')}>Login</button>
                            )}
                            <p>|</p>
                            <button onClick={handleHomeClick}>Home</button>
                            <p>|</p>
                            
                            <button onClick={() => setShowHelp(!showHelp)}>Help</button>
                        </div>
                )}

                {isLoginPage && (
                    <div className='navbar-buttons'>
                        <button onClick={handleHomeClick}>Home</button>
                        <p>|</p>
                        <button onClick={() => setShowHelp(!showHelp)}>Help</button>
                        <button onClick={() => navigate('/caretakerHome')}>TempCT</button>
                    </div>
                )}
            </nav>

            {showHelp && (
                <div className="help">
                    {!showTicketRequest && (
                    <button
                        onClick={() => setShowTicketRequest(true)}
                        className='tktReq'
                    >
                        Request Ticket
                    </button>
                    )}

                    {showTicketRequest && (
                    <form
                        className='help2'
                        onSubmit={async (e) => {
                            e.preventDefault();
                            const ticketTitle = e.target.elements.title.value;
                            const ticketText = e.target.elements.ticket.value;
                            const customerID = "your-customer-id"; // Replace with the actual customer ID

                            try {
                                const response = await fetch('http://localhost:8080/api/create-ticket', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/json',
                                    },
                                    body: JSON.stringify({ ticketTitle, ticketText, customerID }),
                                });

                                if (response.ok) {
                                    const result = await response.text();
                                    console.log(result); // Handle success response
                                    alert('Ticket created successfully!');
                                } else {
                                    const error = await response.text();
                                    console.error('Failed to create ticket:', error);
                                    alert('Failed to create ticket');
                                }
                            } catch (error) {
                                console.error('Error:', error);
                                alert('An error occurred while creating the ticket');
                            }

                            setShowHelp(false);
                            setShowTicketRequest(false);
                        }}
                    >
                        <input
                            name='title'
                            type="text"
                            placeholder='Title:'
                            className='ticketInput'
                        />
                        <input
                            name='ticket'
                            placeholder='Enter Ticket / Problem faced here:'
                            className='ticketInput'
                            required
                        />
                        <button type='submit' className='submitTkt'>Submit</button>
                    </form>
                    )}

                    <button
                    className='closeBtn'
                    onClick={() => {
                        setShowHelp(false);
                        setShowTicketRequest(false);
                    }}
                    >
                    Close
                    </button>
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
