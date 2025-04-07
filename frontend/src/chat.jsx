import "./css/reset.css";
import "./css/chat.css";
import React, { useState } from 'react';

export default function Chat() {
    const [messages, setMessages] = useState([
        { sender: "Brian", text: "Yo let me walk your dog and stuff" },
        { sender: "You", text: "K" },
        { sender: "Brian", text: "Time and place plz x" },
    ]);

    const [contacts] = useState([
        { name: "Brian", unread: 1 },
        { name: "Bianca", unread: 0 },
        { name: "Dave", unread: 0 },
        { name: "PIM Ticket #2312", unread: 0 },
        { name: "Stewie", unread: 0 },
        { name: "Meg", unread: 0 },
    ]);

    return (
        <div className="chat-container">
            <div className="sidebar">
                <input type="text" placeholder="Search..." className="search-bar" />
                <ul className="contact-list">
                    {contacts.map((contact) => (
                        <li key={contact.name} className={`contact ${contact.unread ? "unread" : ""}`}>
                            <span>{contact.name}</span>
                            {contact.unread > 0 && <span className="unread-badge">{contact.unread}</span>}
                        </li>
                    ))}
                </ul>
            </div>
            <div className="chat-window">
                <div className="chat-header">
                    <h2>Brian</h2>
                </div>
                <div className="chat-messages">
                    {messages.map((message, index) => {
                        const type = message.sender === "You" ? "sent" : "received"; // Sent or received message
                        return (
                            <div key={index} className={`message ${type}`}>
                                <p>{message.text}</p>
                            </div>
                        );
                    })}
                </div>
                <div className="chat-input">
                    <input type="text" placeholder="Type a message..." />
                    <button>Send</button>
                </div>
            </div>
        </div>
    );
}