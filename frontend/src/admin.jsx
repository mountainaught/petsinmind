import React, { useState } from "react";
import "./css/reset.css";
import "./css/admin.css";
import "./css/mobile.css";

export const initialSelectionTickets = [
    { id: 101, name: 'John Doe', ticketName: 'Payment Issue' },
    { id: 102, name: 'Jane Smith', ticketName: 'Reimbursement Query' },
    { id: 103, name: 'Bob Johnson', ticketName: 'Password Reset' },
    { id: 104, name: 'Alice Brown', ticketName: 'Technical Support' },
    { id: 105, name: 'Charlie Davis', ticketName: 'Account Issue' },
    { id: 106, name: 'Eva Green', ticketName: 'Service Request' },
    { id: 107, name: 'Frank Harris', ticketName: 'Billing Inquiry' },
    { id: 108, name: 'Grace Lee', ticketName: 'General Query' },
];
export default function Admin() {
    const initialTickets = [
        { id: 342, title: "Payment" },
        { id: 346, title: "Reimbursement" },
        { id: 389, title: "Reimbursement" },
        { id: 412, title: "Password" },
        { id: 418, title: "Pets" }
      ];
    const [managementTickets, setManagementTickets] = useState([]);
    const [selectionTickets, setSelectionTickets] = useState(initialSelectionTickets);
    const handleSelect = (ticketId) => {
        // Find the ticket with the given id
        const ticket = selectionTickets.find((t) => t.id === ticketId);
        if (ticket) {
          // Add it to the management list
          setManagementTickets((prev) => [...prev, ticket]);
          // Remove it from the selection list
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
        link.href = "public/homerCV.pdf";
        link.download = "public/homerCV.pdf";
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
                        <button onClick={() => { /* Your share logic here */ }}>
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
                        <p>{ticket.ticketName}</p>
                        <button onClick={() => handleSelect(ticket.id)}>Select</button>
                    </div>
                    ))
                ) : (
                    <p>No more tickets available for selection.</p>
                )}
                </div>
            </div>


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