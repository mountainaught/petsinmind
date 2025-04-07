import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './css/reset.css'
import './css/index.css';
import Layout from './layout.jsx'
import App from './App.jsx'
import Login from './login.jsx'
import Register from './register.jsx'
import Details from './details.jsx'
import Chat from './chat.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/" element={<App />}/>
          <Route path="/login" element={<Login />}/>
          <Route path="/register" element={<Register />}/>
          <Route path="/details" element={<Details />}/>
          <Route path="/chat" element={<Chat />}/>
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>
)
