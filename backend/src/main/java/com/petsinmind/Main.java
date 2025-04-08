package com.petsinmind;

import com.petsinmind.users.Caretaker;
import com.petsinmind.utils.DatabaseManager;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("DB URL: " + Config.get("db.url"));

        try {
            DatabaseManager db = new DatabaseManager();

            // 🆔 Replace with the UUID of the user you want to fetch
            UUID id = UUID.fromString("077921c7-e55e-4f9b-87cb-9cdb546b5111");

            // 🔍 Set up a Caretaker object with that ID
            Caretaker ct = new Caretaker();
            ct.setUserID(id);

            // 📥 Fetch from DB
            Caretaker found = (Caretaker) db.findUser(ct);

            // 🖨️ Print their name
            System.out.println("Found caretaker:");
            System.out.println("Name: " + found.getFirstName() + " " + found.getLastName());
            System.out.println("Username: " + found.getUserName());

            db.closeConnection();
        } catch (Exception e) {
            System.out.println("❌ Failed to fetch caretaker");
            e.printStackTrace();
        }
    }
}
