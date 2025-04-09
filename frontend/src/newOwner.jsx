import React, { useState } from 'react';
import "./css/reset.css";
import "./css/details.css";
import { useNavigate } from 'react-router-dom';

export default function NewOwner() {

    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080';

    // Test connection first:
    const testConnection = async () => {
    try {
        const res = await fetch(`${apiUrl}/api/test`);  // Add this test endpoint
        console.log("Connection test:", await res.text());
    } catch (err) {
        console.error("Connection failed:", err);
    }
    };
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        phoneNumber: '',
        firstName: '',
        lastName: ''
    });
    const navigate = useNavigate();

    const validateForm = () => {
        const { username, password, email, phoneNumber, firstName, lastName } = formData;
        
        if (!username.trim() || !password.trim() || !email.trim() || 
            !phoneNumber.trim() || !firstName.trim() || !lastName.trim()) {
            alert("Please fill in all fields");
            return false;
        }
        
        if (password.length < 8) {
            alert("Password must be at least 8 characters");
            return false;
        }
        
        if (!/^\S+@\S+\.\S+$/.test(email)) {
            alert("Please enter a valid email");
            return false;
        }

        if (!/^[\d\s\-()+]{8,20}$/.test(phoneNumber)) {
            alert("Phone number must be 8-20 digits and may include +, -, or spaces");
            return false;
          }
        
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        try {
            const response = await fetch('http://localhost:8080/api/register', {
              method: 'POST',
              headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
              },
              body: JSON.stringify(formData),
              credentials: 'include'  // Important for CORS with credentials
            });

            if (response.ok) {
                alert("Registration successful!");
                navigate('/login'); // Redirect to login after success
            } else {
                const error = await response.text();
                alert(`Registration failed: ${error}`);
            }
        } catch (err) {
            alert("An error occurred: " + err.message);
        }
    };

    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormData(prev => ({ ...prev, [id]: value }));
    };

    return (
        <form className="details-container" onSubmit={handleSubmit}>
            <h2>Register as Pet Owner</h2>
            
            {Object.entries(formData).map(([key, value]) => (
                <div className="form-container" key={key}>
                    <input
                        type={key === 'password' ? 'password' : 'text'}
                        id={key}
                        value={value}
                        onChange={handleChange}
                        placeholder={
                            key === 'phoneNumber' ? 'Phone Number' : 
                            key.charAt(0).toUpperCase() + key.slice(1).replace(/([A-Z])/g, ' $1')
                        }
                        required
                    />
                </div>
            ))}
            
            <button type="submit" className="submit-btn">Register</button>
        </form>
    );
}