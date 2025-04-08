import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/bookApt.css';
import dog from './assets/Dog.png';
import marker from './assets/Marker.png';



export default function BookApt() {
    const [walkingIsClicked, setWalkingClicked] = useState(false); // move it here
    const [mindingIsClicked, setMindingClicked] = useState(false); // move it here

    const navigate = useNavigate();
    return (
        <div className='container'>
            <div className='bookapt-container'>
                <div className='left-container'>
                    <input 
                        type="text" 
                        placeholder="Enter your location" 
                        className="location-input"   
                    />

                    <div className='petselect'>this should contain a list of pets to be involved in the appointment</div>

        

                </div>





                <div className='right-container'>
                    <div className='service-selection'>
                        <button className={`service-button ${walkingIsClicked ? 'active' : ''}`} onClick={() => setWalkingClicked(!walkingIsClicked)}>Walking</button>
                        <button className={`service-button ${mindingIsClicked ? 'active' : ''}`} onClick={() => setMindingClicked(!mindingIsClicked)}>Minding</button>
                    </div>
                    <div className='date-picker'>
                        <div className='date-select-container'>
                            <label htmlFor="start-date">Start Date:</label>
                            <input type="date" id="start-date" className="date-input" />
                            <label htmlFor="end-date">End Date:</label>
                            <input type="date" id="end-date" className="date-input" />
                        </div>
                        <button className='service-button'>Pick Dates</button>
                    </div>
                </div>

            </div>
            <button onClick={() => navigate('/findCaretaker')} className='findCt'>Find Caretaker</button>
        </div>
    );
}