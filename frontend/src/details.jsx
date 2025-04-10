import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "./css/reset.css";
import "./css/details.css";

export default function Details() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState(''); // Initially empty values for username and password
    const navigate = useNavigate();

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

        const predefinedUsers = {
            admin: { username: "admin", password: "admin123", homepage: "/admin"},
            caretaker: { username: "caretaker", password: "caretaker123", homepage: "/caretakerHome"},
            petowner: { username: "petowner", password: "petowner123", homepage: "/petownerhome"}
        };

        const user = Object.values(predefinedUsers).find(
            (u) => u.username === username && u.password === password
        );

        if (user) {
            alert("Login successful!");
            localStorage.setItem("homepage", JSON.stringify(user.homepage)); // Store relevant homepage in localStorage
            navigate(user.homepage); // Redirect to the relevant homepage
        } else {
            alert("Invalid username or password");
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
