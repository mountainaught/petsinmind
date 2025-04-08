import React from 'react';
import "./css/reset.css";
import "./css/navbar.css";
import "./css/home.css";
import "./css/mobile.css";
import logo from './assets/Logo.png';
import left from './assets/leftArrow.png';
import right from './assets/rightArrow.png';
import odin from './assets/odin.png';

export default function home() {
    return (
        <div className='home-container'>
            <img src={logo} alt="logo" />
            <div className='box-container'>
                <div className='infobox'>
                    <h1>About us</h1>
                    <p>We are a group of university students working on a software engineering project, we all love our pets so a pet minder app was the obvious choice. We aim to deliver a clean and efficient service for your use and offer the best care for you and your furry friends.</p>
                </div>
                <div className='infobox'>
                    <h1>Meet the Devs' pets</h1>
                    <img src={odin} alt="" />

                    <div className='arrow-container'>
                        <img className='arrow' src={left} alt="" />
                        <img className='arrow' src={right} alt="" />
                    </div>
                </div>
            </div>
        </div>
    );
}