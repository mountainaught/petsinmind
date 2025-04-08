package com.petsinmind;

import com.petsinmind.users.Caretaker;

public interface ApplicationSA {
    // Method to view all applications
    void viewAllApplications();

    // Method to accept an application
    Caretaker AcceptApplication(Application Application);

    // Method to reject an application
    void RejectApplication(Application Application);
}
