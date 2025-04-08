import React from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/findCaretaker.css';
import human from './assets/humanIcon.png';

export default function FindCaretaker() {
    return (
        <div className='petownerhome'>

            <div className='selectBox'>
                <h1>Caretaker Name</h1>

                <img className='icon' src={human} alt="human" />

                <p>bunch of info about human</p>

                <div className='buttons'>
                    <button className='reject'>X</button>
                    <button className='accept'>✔</button>
                </div>
            </div>
            
        </div>
    );
}