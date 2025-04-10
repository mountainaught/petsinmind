import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import './css/reset.css';
import './css/navbar.css';
import './css/index.css';
import './css/bookApt.css';
import dog from './assets/Dog.png';
import marker from './assets/Marker.png';



export default function BookApt() {
  const [location, setLocation] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [walking, setWalking] = useState(false);
  const [minding, setMinding] = useState(false);
  const [selectedPets, setSelectedPets] = useState({
    odin: false,
    auntie: false
  });

  const navigate = useNavigate();

  const handleSubmit = () => {
    // Basic validation
    if (!location || !startDate || !endDate || (!walking && !minding)) {
      alert('Please fill out all fields and select a service.');
      return;
    }

    const selected = Object.values(selectedPets).filter(Boolean);
    if (selected.length === 0) {
      alert('Please select at least one pet.');
      return;
    }

    localStorage.setItem('jobOfferID', 'fake-joboffer-id-123');
    navigate('/findCaretaker');
  };

  const handlePetChange = (pet) => {
    setSelectedPets((prev) => ({
      ...prev,
      [pet]: !prev[pet]
    }));
  };

  return (
    <div className="bookapt-container">
      <div className="bookapt-form-card">

        <input
          type="text"
          placeholder="Enter your location"
          className="bookapt-location-input"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        />

        <div className="bookapt-pet-select">
          <h3>Select Pet(s):</h3>
          <label className="bookapt-pet-option">
            <input
              type="checkbox"
              checked={selectedPets.odin}
              onChange={() => handlePetChange('odin')}
            />
            Odin
          </label>
          <label className="bookapt-pet-option">
            <input
              type="checkbox"
              checked={selectedPets.auntie}
              onChange={() => handlePetChange('auntie')}
            />
            Auntie
          </label>
        </div>

        <div className="bookapt-service-selection">
          <button
            className={`bookapt-service-button ${walking ? 'active' : ''}`}
            onClick={() => {
              setWalking(!walking);
              if (minding) setMinding(false);
            }}
          >
            Walking
          </button>
          <button
            className={`bookapt-service-button ${minding ? 'active' : ''}`}
            onClick={() => {
              setMinding(!minding);
              if (walking) setWalking(false);
            }}
          >
            Minding
          </button>
        </div>

        <div className="bookapt-date-picker">
          <label>Start Date:</label>
          <input
            type="datetime-local"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
          <label>End Date:</label>
          <input
            type="datetime-local"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </div>

        <button className="bookapt-find-ct" onClick={handleSubmit}>
          Pick Dates
        </button>
      </div>
    </div>
  );
}
