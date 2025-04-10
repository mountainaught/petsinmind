import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/bookApt.css';
import dog from './assets/Dog.png';
import marker from './assets/Marker.png';



export default function BookApt() {
    const [walkingIsClicked, setWalkingClicked] = useState(false); // move it here
    const [mindingIsClicked, setMindingClicked] = useState(false); // move it here
    const [location, setLocation] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [selectedPets, setSelectedPets] = useState([]); // you’ll need to populate this from backend


    const navigate = useNavigate();

    async function handleSubmit() {
        const serviceType = walkingIsClicked ? "Walking" : (mindingIsClicked ? "Minding" : null);
        if (!location || !startDate || !endDate || !serviceType) {
          alert("Please fill out all fields");
          return;
        }
      
        try {
          const res = await fetch('http://localhost:8080/api/job-offer', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              petIDs: selectedPets, // this should be a list of UUID strings
              location,
              startDate,
              endDate,
              type: serviceType,
              petownerID: localStorage.getItem("userID") // assuming it's stored here after login
            })
          });
      
          if (res.ok) {
            const jobOffer = await res.json();
            localStorage.setItem("jobOfferID", jobOffer.jobOfferID); // save it for use in /findCaretaker
            navigate('/findCaretaker');
          } else {
            const err = await res.text();
            alert("Failed to create job offer: " + err);
          }
        } catch (err) {
          console.error(err);
          alert("Error creating job offer.");
        }
      }

      useEffect(() => {
        setSelectedPets(["b8a1214e-91b9-4a99-bc9e-22fe162bf69d"]); // mock UUIDs
      }, []);
      
      
    return (
      <div className='container'>
        <div className='bookapt-container'>
          <div className='left-container'>
            <input 
              type="text" 
              placeholder="Enter your location" 
              className="location-input" 
              value={location}
              onChange={(e) => setLocation(e.target.value)}  
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
                <input type="date" id="start-date" className="date-input" value={startDate} onChange={e => setStartDate(e.target.value)} />
                <label htmlFor="end-date">End Date:</label>
                <input type="date" id="end-date" className="date-input" value={endDate} onChange={e => setEndDate(e.target.value)} />
              </div>
              <button className='service-button'>Pick Dates</button>
            </div>
          </div>
        </div>
        <button onClick={handleSubmit} className='findCt'>Find Caretaker</button>
      </div>
    );
}