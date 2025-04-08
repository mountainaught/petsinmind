import React from 'react';
import "./css/reset.css";
import "./css/caretaker.css";

export default function CaretakerHome() {
    return (
        <div className='page'>
            <div className="grid">
                <div className="info-container">
                    <div className="info">
                        <p className="info-heading">Upcoming Appointments</p>
                        <ul>
                            <li>Dave - 23/03/2025 - Walking</li>
                            <li>Bianca - 30/03/2025 - Sitting</li>
                        </ul>
                        <div className="btn-wrapper">
                            <button className="btn">See More</button>
                        </div>
                    </div>
                    <div className="info">
                        <p className="info-heading">Schedule</p>
                        <p>A very good schedule implementation</p>
                        <p>A very good schedule implementation</p>
                        <p>A very good schedule implementation</p>
                        <div className="btn-wrapper">
                            <button className="btn">Edit Schedule</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}