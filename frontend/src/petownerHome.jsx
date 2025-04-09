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
    const [showPetName, setShowPetName] = useState(false);
    const [showAge, setShowAge] = useState(false);
    const [showType, setShowType] = useState(false);
    const [showPhoto, setShowPhoto] = useState(false);



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
                    <form
                        className='petInfo'
                        onSubmit={(e) => {
                            e.preventDefault();
                            const name = e.target.elements.name.value;
                            const age = e.target.elements.age.value;
                            const type = e.target.elements.type.value;
                            const photo = e.target.elements.photo.value;

                            console.log({ name, age, type, photo });

                            setShowEditPets(false);
                        }}
                        >
                        <div className='name'>
                            {!showPetName && <p>name: input name from db</p>}
                            <input
                            type='text'
                            name='name'
                            placeholder='Enter new name'
                            style={{ display: showPetName ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowPetName(!showPetName)}>Change</button>
                        </div>

                        <div className='age'>
                            {!showAge && <p>age: input age from db</p>}
                            <input
                            type='text'
                            name='age'
                            placeholder='Enter new age'
                            style={{ display: showAge ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowAge(!showAge)}>Change</button>
                        </div>

                        <div className='type'>
                            {!showType && <p>type: input type from db</p>}
                            <input
                            type='text'
                            name='type'
                            placeholder='Enter new type'
                            style={{ display: showType ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowType(!showType)}>Change</button>
                        </div>

                        <div className='photo'>
                            {!showPhoto && <p>photo: input photo from db</p>}
                            <input
                            type='text'
                            name='photo'
                            placeholder='Enter photo URL'
                            style={{ display: showPhoto ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowPhoto(!showPhoto)}>Change</button>
                        </div>

                        <div className='saveCancel'>
                            <button type='submit' className='saveBtn'>Save</button>
                            <button
                            className='cancelBtn'
                            type='button'
                            onClick={() => setShowEditPets(false)}
                            >
                            Cancel
                            </button>
                        </div>
                    </form>            
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