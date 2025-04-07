import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/petowner.css';
import left from './assets/LeftArrow.png';
import right from './assets/RightArrow.png'; 
import human from './assets/humanIcon.png';
import dog from './assets/dogIcon.png';

export default function PetOwnerHome() {
    const location = useLocation();
    const navigate = useNavigate();
    return (
        <div className='petownerhome'>

            <div className='selectBox'>
                <h1>Dog Name</h1>


                <img className='icon' src={dog} alt="dog" />

                <p>bunch of info about dog</p>

                <div className='buttons'>
                    <button className='left'><img src={left} alt="Left Arrow" /></button>
                    <button className='middleBtn'>Edit Pets</button>
                    <button className='right'><img src={right} alt="Right Arrow" /></button>
                </div>
            </div>
            <div className='selectBox'>
                <h1>Caretaker Name</h1>

                <img className='icon' src={human} alt="human" />

                <p>bunch of info about human</p>

                <div className='buttons'>
                    <button className='left'><img src={left} alt="Left Arrow" /></button>
                    <button className='middleBtn'>Rebook</button>
                    <button className='right'><img src={right} alt="Right Arrow" /></button>
                </div>
            </div>

            <button onClick={() => navigate('/bookapt')} className='bookApt'>Book Appointment</button>
        </div>
    );
}