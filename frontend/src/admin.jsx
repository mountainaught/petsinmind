import React, { useState } from "react";
import "./css/reset.css";
import "./css/admin.css";
import "./css/mobile.css";
import { useNavigate } from 'react-router-dom';


export default function Admin() {
    const initialTickets = [
        { id: 342, title: "Payment" },
        { id: 346, title: "Reimbursement" },
        { id: 389, title: "Reimbursement" },
        { id: 412, title: "Password" },
        { id: 418, title: "Pets" }
      ];
    const initialSelectionTickets = [
        { id: 101, name: 'John Doe', ticketName: 'Payment Issue' },
        { id: 102, name: 'Jane Smith', ticketName: 'Reimbursement Query' },
        { id: 103, name: 'Bob Johnson', ticketName: 'Password Reset' },
        { id: 104, name: 'Alice Brown', ticketName: 'Technical Support' },
        { id: 105, name: 'Charlie Davis', ticketName: 'Account Issue' },
        { id: 106, name: 'Eva Green', ticketName: 'Service Request' },
        { id: 107, name: 'Frank Harris', ticketName: 'Billing Inquiry' },
        { id: 108, name: 'Grace Lee', ticketName: 'General Query' },
    ];
    const [selectionTickets, setSelectionTickets] = useState(initialSelectionTickets);
    const [showSharePopup, setShowSharePopup] = useState(false);
    const [selectedTicketId, setSelectedTicketId] = useState(null);
    const handleSelect = (ticketId) => {
        const ticket = selectionTickets.find((t) => t.id === ticketId);
        if (ticket) {
          // Convert it to the format used by "tickets"
          const newTicket = {
            id: ticket.id,
            title: ticket.ticketName,
          };
      
          // Add to the open tickets (My Ticket Management)
          setTickets((prev) => [...prev, newTicket]);
      
          // Remove from selection list
          setSelectionTickets((prev) => prev.filter((t) => t.id !== ticketId));
        }
      };
    const [tickets, setTickets] = useState(initialTickets);
    const closeTicket = (ticketId) => {
        setTickets(prevTickets =>
          prevTickets.filter(ticket => ticket.id !== ticketId)
        );
      };
    const [applications, setApplications] = useState([
        { id: 1, name: 'Alice Walker' },
        { id: 2, name: 'Brian Murphy' },
        { id: 3, name: 'Clara Chen' },
        { id: 4, name: 'David Patel' }
    ]);
    
    const handleDecision = (id, decision) => {
        const applicant = applications.find(app => app.id === id);
        alert(`Application for ${applicant.name} has been ${decision}.`);
        setApplications(prev => prev.filter(app => app.id !== id));
    };
    
    const downloadCV = () => {
        const link = document.createElement('a');
        link.href = "petsinmind\frontend\public\homerCV.pdf"; 
        link.download = "petsinmind\frontend\public\homerCV.pdf";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };
    return (
        <div className="admin-container">
            {/* ticket management */}
            <div className="box">
                <h1>My Ticket Management</h1>
                <div className="open-tickets">
                    {tickets.map(ticket => (
                    <div key={ticket.id} className="exampleTicket">
                        <h2>Ticket #{ticket.id}</h2>
                        <p>{ticket.title}</p>
                        <button onClick={() => { setSelectedTicketId(ticket.id);
  setShowSharePopup(true);}}>
                        Share
                        </button>
                        <button onClick={() => closeTicket(ticket.id)}>
                        Close
                        </button>
                    </div>
                    ))}
                </div>
            </div>

            {/* Ticket Selection Box */}
            <div className="box">
                <h1>Ticket Selection</h1>
                <div className="selected-tickets">
                {selectionTickets.length > 0 ? (
                    selectionTickets.map((ticket) => (
                        <div key={ticket.id} className="exampleTicket">
                        <h2>{ticket.name}</h2>
                        <div className="ticket-row">
                            <p>{ticket.ticketName}</p>
                            <button onClick={() => handleSelect(ticket.id)}>Select</button>
                        </div>
                    </div>
                    ))
                ) : (
                    <p>No more tickets available for selection.</p>
                )}
                </div>
            </div>
            {showSharePopup && (
                <div className="popup-overlay">
                    <div className="popup">
                    <h2>Select an Admin to Share Ticket #{selectedTicketId}</h2>
                    {["MarryPoppin", "Napoleon ", "StarDestroyer123 "].map((admin, index) => (
                        <div key={index} className="admin-option">
                        <p>{admin}</p>
                        <button
                            onClick={() => {
                            alert(`Ticket #${selectedTicketId} has been shared with ${admin}`);
                            setShowSharePopup(false);
                            setSelectedTicketId(null);
                            }}
                        >
                            Select
                        </button>
                        </div>
                    ))}
                    </div>
                </div>
            )}

            {/* Application Selection */}
            <div className='box'>
                <h1>Application Selection</h1>
                <div className='applications'>
                    {applications.map(app => (
                    <div className="exampleApp">
                        <p>{app.name}</p>
                        <button onClick={downloadCV}>Download CV</button>
                        <button onClick={() => handleDecision(app.id, 'approved')}>Approve</button>
                        <button onClick={() => handleDecision(app.id, 'rejected')}>Reject</button>
                    </div>
                    ))}
                </div>
            </div>
            
        </div>

        
    )
}