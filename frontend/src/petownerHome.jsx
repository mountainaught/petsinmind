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
import auntie from './assets/auntie.png';
import odin from './assets/odin.png';


export default function PetOwnerHome() {
    const location = useLocation();
    const navigate = useNavigate();

    const [showEditPets, setShowEditPets] = useState(false);
    const [showEditProfile, setShowEditProfile] = useState(false);
    const [showPetName, setShowPetName] = useState(false);
    const [showAge, setShowAge] = useState(false);
    const [showType, setShowType] = useState(false);
    const [showPhoto, setShowPhoto] = useState(false);
    const [showAddPet, setShowAddPet] = useState(false);



    /*THIS IS AN EXAMPLE BUT YOU SHOULD BE ABLE TO ASSIGN THESE VALUES FROM THE DATABASE*/
    const [dogs, setDogs] = useState([
        {
            name: "Auntie",
            age: 3,
            type: "Corthal Griffon",
            photo: auntie
        },
        {
            name: "Odin",
            age: 5,
            type: "Working cocker spaniel",
            photo: odin
        }
    ]);
    


    const [currentDogIndex, setCurrentDogIndex] = useState(0);

    const handleNextDog = () => {
        setCurrentDogIndex((prevIndex) => (prevIndex + 1) % dogs.length);
    };

    const handlePreviousDog = () => {
        setCurrentDogIndex((prevIndex) => (prevIndex - 1 + dogs.length) % dogs.length);
    };

    const currentDog = dogs[currentDogIndex];

    return (
        <div className='petownerhome'>
            <div className='selectBox'>
                <h1>{currentDog.name}</h1>

                <img className='icon' src={currentDog.photo} alt={currentDog.name} />

                <p>Age: {currentDog.age}</p>
                <p>Type: {currentDog.type}</p>

                <div className='buttons'>
                    <button className='left' onClick={handlePreviousDog}>
                        <img src={left} alt="Left Arrow" />
                    </button>

                    <button className='middleBtn'
                        onClick={() => {
                            setShowAddPet(!showEditPets);
                            setShowEditProfile(false);
                        }}
                    
                    >Add Pet</button>
                    <button
                        className='middleBtn'
                        onClick={() => {
                            setShowEditPets(!showEditPets);
                            setShowEditProfile(false);
                            setShowAddPet(false);
                        }}
                    >
                        Edit Pet
                    </button>
                    <button className='right' onClick={handleNextDog}>
                        <img src={right} alt="Right Arrow" />
                    </button>
                </div>
            </div>

            <div className='selectBox'>
                <h1>Caretaker Name</h1>

                <img className='icon' src={human} alt="human" />

                <p>bunch of info about human</p>

                <div className='buttons'>
                    <button className='left'>
                        <img src={left} alt="Left Arrow" />
                    </button>
                    <button className='middleBtn'>Rebook</button>
                    <button className='right'>
                        <img src={right} alt="Right Arrow" />
                    </button>
                </div>
            </div>
            <div className='rightButtons'>
                <button onClick={() => navigate('/bookApt')} className='bookApt'>
                    Book Appointment
                </button>
                <button
                    className='editProfile'
                    onClick={() => {
                        setShowEditProfile(!showEditProfile);
                        setShowEditPets(false);
                        setShowAddPet(false);
                    }}
                >
                    Edit Profile
                </button>
            </div>

            {showEditPets && (
                <div className='editPets'>
                    <p>Edit Pets</p>
                    <form
                        className='petInfo'
                        onSubmit={(e) => {
                            e.preventDefault();
                            const name = e.target.elements.name.value || currentDog.name;
                            const age = e.target.elements.age.value || currentDog.age;
                            const type = e.target.elements.type.value || currentDog.type;
                            const photo = e.target.elements.photo.files[0]
                                ? URL.createObjectURL(e.target.elements.photo.files[0])
                                : currentDog.photo;

                            const updatedDogs = [...dogs];
                            updatedDogs[currentDogIndex] = { name, age, type, photo };
                            setDogs(updatedDogs);
                            setShowEditPets(false);
                        }}
                    >
                        <div className='name'>
                            {!showPetName && <p>name: {currentDog.name}</p>}
                            <input
                                type='text'
                                name='name'
                                placeholder='Enter new name'
                                style={{ display: showPetName ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowPetName(!showPetName)}>
                                Change
                            </button>
                        </div>

                        <div className='age'>
                            {!showAge && <p>age: {currentDog.age}</p>}
                            <input
                                type='text'
                                name='age'
                                placeholder='Enter new age'
                                style={{ display: showAge ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowAge(!showAge)}>
                                Change
                            </button>
                        </div>

                        <div className='type'>
                            {!showType && <p>type: {currentDog.type}</p>}
                            <input
                                type='text'
                                name='type'
                                placeholder='Enter new type'
                                style={{ display: showType ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowType(!showType)}>
                                Change
                            </button>
                        </div>

                        <div className='photo'>
                            {!showPhoto && <p>photo: {currentDog.photo}</p>}
                            <input
                                type='file'
                                name='photo'
                                placeholder='Enter photo URL'
                                style={{ display: showPhoto ? 'block' : 'none' }}
                            />
                            <button type='button' onClick={() => setShowPhoto(!showPhoto)}>
                                Change
                            </button>
                        </div>

                        <div className='saveCancel'>
                            <button type='submit' className='saveBtn'>
                                Save
                            </button>


                            {/*THIS IS A FRONTEND SOLUTION AND WILL NOT CHANGE BACKEND*/}
                            <button
                                type='button'
                                className='deletePet'
                                onClick={() => {
                                    const updatedDogs = dogs.filter((_, index) => index !== currentDogIndex);
                                    setDogs(updatedDogs);
                                    setCurrentDogIndex((prevIndex) =>
                                        prevIndex === updatedDogs.length ? 0 : prevIndex
                                    );
                                    setShowEditPets(false);
                                }}
                            >
                                Delete Pet
                            </button>
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

            {showAddPet && (
                <div className='editPets'>
                    <p>Add Pet</p>
                    <form
                        className='petInfo'
                        onSubmit={(e) => {
                            e.preventDefault();
                            const name = e.target.elements.name.value;
                            const age = e.target.elements.age.value;
                            const type = e.target.elements.type.value;
                            const photo = e.target.elements.photo.files[0]
                                ? URL.createObjectURL(e.target.elements.photo.files[0])
                                : null;

                            // Add the new pet to the dogs array
                            const newDog = { name, age, type, photo };
                            setDogs((prevDogs) => [...prevDogs, newDog]);

                            setShowAddPet(false); // Close the form after adding
                        }}
                    >
                        <div className='name'>
                            <input
                                type='text'
                                name='name'
                                placeholder='Enter pet name'
                                required
                            />
                        </div>

                        <div className='age'>
                            <input
                                type='text'
                                name='age'
                                placeholder='Enter pet age'
                                required
                            />
                        </div>

                        <div className='type'>
                            <input
                                type='text'
                                name='type'
                                placeholder='Enter pet type'
                                required
                            />
                        </div>

                        <div className='photo'>
                            <input
                                type='file'
                                name='photo'
                                placeholder='Upload photo'
                                accept="image/*"
                            />
                        </div>

                        <div className='saveCancel'>
                            <button type='submit' className='saveBtn'>
                                Add Pet
                            </button>

                            <button
                                className='cancelBtn'
                                type='button'
                                onClick={() => setShowAddPet(false)}
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