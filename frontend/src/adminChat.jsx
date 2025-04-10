import "./css/reset.css";
import "./css/chat.css";
import "./css/mobile.css";
import React, { useState, useEffect } from "react";
import { initialSelectionTickets } from "./admin";

export default function AdminChat() {
    const [messages, setMessages] = useState([]);
    const [contacts, setContacts] = useState(
        initialSelectionTickets.map((ticket) => ({
            name: `${ticket.name} - ${ticket.ticketName}`,
            unread: 0,
        }))
    );
    const [newMessage, setNewMessage] = useState("");
    const [activeContact, setActiveContact] = useState(
        contacts.length > 0 ? contacts[0].name : "" // Default to the first contact
    );

    // Load messages from local storage on component mount
    useEffect(() => {
        const savedMessages = localStorage.getItem("chatMessages");
        if (savedMessages) {
            setMessages(JSON.parse(savedMessages));
        }
    }, []);

    // Reset unread count when entering a chat
    useEffect(() => {
        setContacts((prevContacts) =>
            prevContacts.map((contact) =>
                contact.name === activeContact ? { ...contact, unread: 0 } : contact
            )
        );
    }, [activeContact]);

    const handleSendMessage = () => {
        if (newMessage.trim() !== "") {
            const updatedMessages = [
                ...messages,
                { sender: "You", recipient: activeContact, text: newMessage },
            ];
            setMessages(updatedMessages);
            setNewMessage("");

            // Save messages to local storage
            localStorage.setItem("chatMessages", JSON.stringify(updatedMessages));

            // Simulate a reply
            setTimeout(() => {
                const replyMessages = [
                    ...updatedMessages,
                    { sender: activeContact, recipient: "You", text: "HELP!!!!" },
                ];
                setMessages(replyMessages);

                // Increment unread count for the active contact
                setContacts((prevContacts) =>
                    prevContacts.map((contact) =>
                        contact.name === activeContact
                            ? { ...contact, unread: contact.unread + 1 }
                            : contact
                    )
                );

                // Save the reply to local storage
                localStorage.setItem("chatMessages", JSON.stringify(replyMessages));
            }, 1000); // Simulate a 1-second delay for the reply
        }
    };

    const clearAllMessages = () => {
        setMessages([]); // Clear the messages state
        localStorage.removeItem("chatMessages"); // Remove messages from localStorage
    };

    return (
        <div className="chat-container">
            <div className="sidebar">
                <input type="text" placeholder="Search..." className="search-bar" />
                <ul className="contact-list">
                    {contacts.map((contact) => (
                        <li
                            key={contact.name}
                            className={`contact ${contact.unread ? "unread" : ""} ${
                                activeContact === contact.name ? "active" : ""
                            }`}
                            onClick={() => setActiveContact(contact.name)} // Update active contact
                        >
                            <span>{contact.name}</span>
                            {contact.unread > 0 && <span className="unread-badge">{contact.unread}</span>}
                        </li>
                    ))}
                </ul>
            </div>
            <div className="chat-window">
                <button onClick={clearAllMessages} className="clear-all-button">Clear All</button>
                <div className="chat-header">
                    <h2>{activeContact}</h2>
                </div>
                <div className="chat-messages">
                    {messages
                        .filter((message) => message.recipient === activeContact || message.sender === activeContact)
                        .map((message, index) => {
                            const type = message.sender === "You" ? "sent" : "received";
                            return (
                                <div key={index} className={`message ${type}`}>
                                    <p>{message.text}</p>
                                </div>
                            );
                        })}
                </div>
                <div className="chat-input">
                    <input
                        type="text"
                        placeholder="Type a message..."
                        value={newMessage}
                        onChange={(e) => setNewMessage(e.target.value)}
                    />
                    <button onClick={handleSendMessage}>Send</button>
                </div>
            </div>
        </div>
    );
}