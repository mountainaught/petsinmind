import React from 'react';
import "./css/reset.css";
import "./css/navbar.css";
import "./css/home.css";
import "./css/mobile.css";
import logo from './assets/Logo.png';
import left from './assets/leftArrow.png';
import right from './assets/rightArrow.png';


/*Importing pets*/
import odin from './assets/odin.png';
import Eftichis from './assets/Eftichis.png';
import SirSmoky from './assets/sirsmoky.png';
import Michi from './assets/michi.png';
import Auntie from './assets/auntie.png';

const pets = [
    { name: 'Odin', image: odin },
    { name: 'Eftichis', image: Eftichis },
    { name: 'Sir Smoky', image: SirSmoky },
    { name: 'Michi', image: Michi },
    { name: 'Auntie', image: Auntie }

];
const petIndex = 0;
const pet = pets[petIndex];



export default function home() {
    const [petIndex, setPetIndex] = React.useState(0);
    const pet = pets[petIndex];

    const handleNextPet = () => {
        setPetIndex((prevIndex) => (prevIndex + 1) % pets.length);
    };

    const handlePreviousPet = () => {
        setPetIndex((prevIndex) => (prevIndex - 1 + pets.length) % pets.length);
    };

    return (
        <div className='home-container'>
            <img className='homeLogo' src={logo} alt="logo" />
            <div className='box-container'>
                <div className='infoboxLeft'>
                    <h1>About us</h1>
                    <p>We are a group of university students working on a software engineering project, we all love our pets so a pet minder app was the obvious choice. We aim to deliver a clean and efficient service for your use and offer the best care for you and your furry friends.</p>
                </div>
                <div className='infobox'>
                    <h1>Meet the Devs' pets</h1>
                    
                    <div className='petImg-container'>
                        <img className='petImg' src={pet.image} alt={pet.name} />
                    </div>
                    <div className='arrow-container'>
                        <img className='arrow' src={left} alt="Previous" onClick={handlePreviousPet} />
                        <img className='arrow' src={right} alt="Next" onClick={handleNextPet} />
                    </div>
                </div>
            </div>
        </div>
    )
}