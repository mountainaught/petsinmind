import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/petowner.css';
import './css/mobile.css'
import left from './assets/LeftArrow.png';
import right from './assets/RightArrow.png'; 
import human from './assets/humanIcon.png';
import dog from './assets/dogIcon.png';



export default function PetOwnerHome() {
    const location = useLocation();
    const navigate = useNavigate();

    const [showEditPets, setShowEditPets] = useState(false);
    const [showEditProfile, setShowEditProfile] = useState(false);
    return (
        <div className='petownerhome'>

            <div className='selectBox'>
                <h1>Dog Name</h1>


                <img className='icon' src={dog} alt="dog" />

                <p>bunch of info about dog</p>

                <div className='buttons'>
                    <button className='left'><img src={left} alt="Left Arrow" /></button>
                    <button className='middleBtn' onClick={() => {setShowEditPets(!showEditPets);setShowEditProfile(false)}}>Edit Pets</button>
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
            <div className='rightButtons'>
                <button onClick={() => navigate('/bookapt')} className='bookApt'>Book Appointment</button>
                <button className='editProfile' onClick={() => {setShowEditProfile(!showEditProfile); setShowEditPets(false)}}>Edit Profile</button>
            </div>




            {showEditPets && (
                <div className='editPets'>
                    <p>Edit Pets</p>
                    <select>
                        <option value="dog1">Dog 1</option>
                        <option value="dog2">Dog 2</option>
                        <option value="dog3">Dog 3</option>
                    </select>
                    <div className='petInfo'>
                        <div className='name'>
                            <p>name: input name from db</p>
                            <button>Change</button>
                        </div>

                        <div className='age'>
                            <p>age: input age from db</p>
                            <button>Change</button>
                        </div>

                        <div className='type'>
                            <p>type: input type from db</p>
                            <button>Change</button>
                        </div>

                        <div className='photo'>
                            <p>photo: input photo from db</p>
                            <button>Change</button>
                        </div>
                    </div>



                    <div className='petEdit'>


                    </div>
                </div>

            )}

            {showEditProfile && (
                <div className='editProfileOption'>
                    <div className='profilePic'>
                        <p>insert profile picture here</p>
                        <button>Change</button>
                    </div>
                </div>
                

            )}

        </div>
    );
}