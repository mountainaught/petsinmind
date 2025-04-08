import React from 'react';
import "./css/reset.css";
import "./css/navbar.css";
import "./css/home.css";
import logo from './assets/Logo.png';
import left from './assets/leftArrow.png';
import right from './assets/rightArrow.png';

export default function home() {
    return (
        <div className='home-container'>
            <img src={logo} alt="logo" />
            <div className='box-container'>
                <div className='infobox'>
                    <h1>About us</h1>
                    <p>Fill with a whole bunch of info</p>
                </div>
                <div className='infobox'>
                    <h1>Meet the Devs' pets</h1>
                    <div className='arrow-container'>
                        <img src={left} alt="" />
                        <img src={right} alt="" />
                    </div>
                </div>
            </div>
        </div>
    );
}