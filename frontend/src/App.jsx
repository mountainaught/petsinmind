import React from 'react';
import "./css/reset.css";
import "./css/navbar.css";
import "./css/index.css";
import logo from './assets/Logo.png';

function App() {
    return (
        <div>
            <nav className='navbar'>
                <div className='logoandtitle'>
                    <img src={logo} alt="Logo" />
                    <h1>No Page Yet</h1>
                </div>

                <div className='navbar-buttons'>
                
                    <button>Login</button>
                    <p>|</p>
                    <button>Home</button>
                    <p>|</p>
                    <button>Help</button>
                    
                </div>
            </nav>
            <img className = "bgImage" src="/src/assets/paws.png" alt="Paws" />
        </div>
    );
}

export default App;