import React from 'react';
import "./css/reset.css";
import "./css/admin.css";
import "./css/mobile.css";
import { useNavigate } from 'react-router-dom';


export default function Admin() {
    return(
        <div className="admin-container">
            {/* ticket management */}
            <div className='box'>
                <h1>Ticket Management</h1>

                <div className='open-tickets'>
                    <div className='exampleTicket'>Ticket #1</div>
                    <div className='exampleTicket'>Ticket #2</div>
                    <div className='exampleTicket'>Ticket #3</div>
                    <div className='exampleTicket'>Ticket #4</div>
                    <div className='exampleTicket'>Ticket #5</div>
                    <div className='exampleTicket'>Ticket #6</div>
                    <div className='exampleTicket'>Ticket #7</div>
                    <div className='exampleTicket'>Ticket #8</div>
                    <div className='exampleTicket'>Ticket #9</div>
                    <div className='exampleTicket'>Ticket #10</div>
                    <div className='exampleTicket'>Ticket #11</div>
                    <div className='exampleTicket'>Ticket #12</div>
                    <div className='exampleTicket'>Ticket #13</div>
                </div>



            </div>

            {/* ticket selection */}
            <div className='box'>
                <h1>Ticket Selection</h1>

                <div className='selected-tickets'>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>

                    <div className='exampleTicket'>
                        <p>Ticket Name</p>
                        <button>select</button>
                    </div>
                </div>
            </div>

            {/* Application Selection */}
            <div className='box'>
                <h1>Application Selection</h1>
                <div className='applications'>

                    <div className='exampleApp'>
                        <p>Name</p>
                        <button>Download cv</button>
                        <button>approve</button>
                        <button>reject</button>
                    </div>

                    <div className='exampleApp'>
                        <p>Name</p>
                        <button>Download cv</button>
                        <button>approve</button>
                        <button>reject</button>
                    </div>

                    <div className='exampleApp'>
                        <p>Name</p>
                        <button>Download cv</button>
                        <button>approve</button>
                        <button>reject</button>
                    </div>

                    <div className='exampleApp'>
                        <p>Name</p>
                        <button>Download cv</button>
                        <button>approve</button>
                        <button>reject</button>
                    </div>

                    <div className='exampleApp'>
                        <p>Name</p>
                        <button>Download cv</button>
                        <button>approve</button>
                        <button>reject</button>
                    </div>

                    <div className='exampleApp'>
                        <p>Name</p>
                        <button>Download cv</button>
                        <button>approve</button>
                        <button>reject</button>
                    </div>
                </div>
            </div>

            {/* log */}
            <div className='box'>
                <h1>Log</h1>
                <div className='options'>
                    <div className='searchBy'>
                        <p>Search By:</p>
                        <button>username</button>
                        <button>id</button>
                    </div>
                    <div className='searchFor'>
                        <p>Search For:</p>
                        <button>User</button>
                        <button>Appointment</button>
                        <button>Job Offer</button>
                    </div>
                    <div className='searchBar'>
                        <input type="text" placeholder="Search..." />
                        <button>Search</button>
                    </div>
                </div>

                <div className='returnedData'>

                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                    <div className='exampleLog'>
                        <p>Appointment</p>
                        <p>Username/relevant data</p>
                    </div>
                </div>
            </div>
        </div>

        
    )





}