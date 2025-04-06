import React, { useState } from 'react';
import "./css/reset.css";
import "./css/details.css";

export default function Details() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState(''); // Initially empty values for username and password

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Username:', username);
        console.log('Password:', password); // Temporary stuff for debugging
    };

    return (
        <form className="details-container" onSubmit={handleSubmit}>
            <h2>Enter Details</h2>
            <div className="form-container">
                <input
                    type="text"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Username"
                    required
                />
            </div>
            <div className="form-container">
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                />
            </div>
            <button type="submit" className="submit-btn">Submit</button>
        </form>
    );
};
