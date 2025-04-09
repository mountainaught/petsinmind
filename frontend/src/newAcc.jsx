import React, { useEffect, useState } from 'react';
import "./css/reset.css";
import "./css/details.css";
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

export default function NewAcc() {

    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080';
    const location = useLocation();
    const navigate = useNavigate();

    // Read role from query param
    const queryParams = new URLSearchParams(location.search);
    const role = queryParams.get('role'); // 'owner' or 'caretaker'

    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        phoneNumber: '',
        firstName: '',
        lastName: '',
        location: '',
        ...(role === 'caretaker' && { CV: null }) // Will be a pdf file upload
    });

    useEffect(() => {
        if (!role) {
          alert("No role specified. Redirecting to register page.");
          navigate("/register");
        }
      }, [role, navigate]); // In case something goes wrong and there is no role

    const validateForm = () => {
        const { username, password, email, phoneNumber, firstName, lastName, location, CV } = formData;
        
        if (!username.trim() || !password.trim() || !email.trim() || 
            !phoneNumber.trim() || !firstName.trim() || !lastName.trim() || 
            !location.trim() || !CV) {
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

        if (location.trim().length === 0) {
            alert("Location is required for pet owners");
            return false;
        }


        
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        try {
            const endpoint = role === "caretaker" ? "/api/register-caretaker" : "/api/register-owner";

            const toSend = new FormData();
            Object.entries(formData).forEach(([key, value]) => {
                if (value != null) {
                    toSend.append(key, value);
                }
            });

            const response = await fetch(`${apiUrl}${endpoint}`, {
                method: 'POST',
                body: toSend,
                credentials: 'include'  // Important for CORS with credentials
              });

            if (response.ok) {
                alert(`${role.charAt(0).toUpperCase() + role.slice(1)}egistration successful!`);
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

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file && file.type === "application/pdf") {
            setFormData((prev) => ({ ...prev, CV: file }));
        } else {
            alert("Please upload a valid PDF file.");
        }
    };

    return (
        <form className="details-container" onSubmit={handleSubmit}>
            <h2>{role === 'owner' ? 'Register as Pet Owner' : 'Register as Caretaker'}</h2>
            
            {Object.entries(formData).map(([key, value]) => {
                if (key === 'CV') return null; // No text input for CV
                return (
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
                );
            })}

            {role === 'caretaker' && (
                <div className="form-container">
                    <label htmlFor="pdfFile">Upload CV as PDF:</label>
                    <input
                        type="file"
                        id="CV"
                        accept="application/pdf"
                        onChange={handleFileChange}
                        required
                    />
                </div>
            )}
            <button type="submit" className="submit-btn">Register</button>
        </form>
    );
}