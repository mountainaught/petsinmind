import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './css/findCaretaker.css'; // Our specialized CSS for caretaker
import omar from './assets/omar.jpg';
import sarah from './assets/sarah.jpg';
import anna from './assets/anna.jpg';

// Example caretaker data
const caretakers = [
  { id: 'ct-001', name: 'Sarah Johnson', info: '5 years experience, loves dogs', photo: sarah },
  { id: 'ct-002', name: 'Omar Al-Fulan', info: 'Certified trainer, available weekends', photo: omar },
  { id: 'ct-003', name: 'Anna Smith', info: 'Vet student, specializes in elderly pets', photo: anna }
];

export default function FindCaretaker() {
  const navigate = useNavigate();
  const [index, setIndex] = useState(0);
  const [accepted, setAccepted] = useState([]);
  const [rejected, setRejected] = useState([]);
  const [showSummary, setShowSummary] = useState(false);

  // The caretaker we are currently viewing
  const current = caretakers[index];

  function handleDecision(type) {
    if (type === 'accept') {
      setAccepted([...accepted, current]);
    } else {
      setRejected([...rejected, current]);
    }

    // Move to the next caretaker or show summary if done
    if (index + 1 < caretakers.length) {
      setIndex(index + 1);
    } else {
      setShowSummary(true);
    }
  }

  function handleSendOffers() {
    alert(`✅ Sent job offers to: ${accepted.map(c => c.name).join(', ')}`);
    navigate('/petownerhome');
  }

  return (
    <div className="findcaretaker-container">
      {!showSummary ? (
        <div className="findcaretaker-selectBox">
          <h1>{current.name}</h1>
          <img className="findcaretaker-icon" src={current.photo} alt="caretaker" />
          <p>{current.info}</p>

          <div className="findcaretaker-buttons">
            <button className="findcaretaker-reject" onClick={() => handleDecision('reject')}>
              X
            </button>
            <button className="findcaretaker-accept" onClick={() => handleDecision('accept')}>
              ✔
            </button>
          </div>
        </div>
      ) : (
        <div className="findcaretaker-summary">
          <h2>✅ Accepted</h2>
          <ul>
            {accepted.map(c => (
              <li key={c.id}>{c.name}</li>
            ))}
          </ul>

          <h2>❌ Rejected</h2>
          <ul>
            {rejected.map(c => (
              <li key={c.id}>{c.name}</li>
            ))}
          </ul>

          <button className="findcaretaker-finalize" onClick={handleSendOffers}>
            Send Job Offers
          </button>
        </div>
      )}
    </div>
  );
}
