import React, { useState } from 'react';
import "./css/reset.css";
import "./css/details.css";

export default function Details() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState(''); // Initially empty values for username and password

    const validateForm = () => {
        if (!username.trim() || !password.trim()) {
          alert("Please fill in all fields");
          return false;
        }
        if (password.length < 8) {
          alert("Password must be at least 8 characters");
          return false;
        }
        return true;
      };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL || 'http://localhost:8080'}/api/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            });
    
            if (response.ok) {
                const text = await response.text();
                alert(text); // Success
            } else {
                const error = await response.text();
                alert("Login failed: " + error);
            }
        } catch (err) {
            alert("An error occurred: " + err.message);
        }
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
