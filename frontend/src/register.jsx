import React from 'react';
import "./css/reset.css";
import "./css/register.css";

export default function Register() {
    return (
        <div className = "options-grid">

            <div className="option-container">
                <p className = "option-heading">Pet-Owner</p>
                <img className="option-img" src="/src/assets/Pet-Owner_example.png" alt="Pet-Owner"/>
                <ul className = "option-text"> 
                    <li>Get access to top notch pet care from vetted caretakers</li>
                    <li>Feel at ease while your fluffy friends are away with live tracking and updates</li>
                    <li>Message your caretaker to get updates or message us if you have any issues</li>
                </ul>
                <button className='option-btn'>Register as Pet-Owner</button>
            </div>

            <div className="option-container">
                <p className = "option-heading">Caretaker</p>
                <img className="option-img" src="/src/assets/Caretaker_example.png" alt="Caretaker"/>
                <ul className = "option-text"> 
                    <li>Set your own hours</li>
                    <li>Set your own pay</li>
                    <li>Hang out with cute critters</li>
                    <li>Support from our team of pet owners</li>
                    <li>Make friends with pet owners (They might book you again!)</li>
                </ul>
                <button className='option-btn align-options'>Register as Caretaker</button>
            </div>
        </div>
    );
}