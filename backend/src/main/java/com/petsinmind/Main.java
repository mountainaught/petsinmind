package com.petsinmind;
import java.util.UUID;

import com.petsinmind.users.Caretaker;
import com.petsinmind.utils.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("DB URL: " + Config.get("db.url"));
        try {

            Caretaker newCT = new Caretaker();
            newCT.setUserID(UUID.randomUUID());

            DatabaseManager db = new DatabaseManager();
            db.findByID(newCT);

            // 🔸 Create new caretaker object
            Caretaker ct = new Caretaker();
            ct.setUserID(UUID.randomUUID());
            ct.setUserName("laila");
            ct.setUserPassword("password123");
            ct.setUserEmail("laila@example.com");
            ct.setPhoneNumber("66667777");
            ct.setFirstName("Laila");
            ct.setLastName("Kuwaiti");
            ct.setLocation("Kuwait City");
            
            ct.setPay(400.0f);

        } catch (Exception e) {
            System.out.println("❌ Failed to connect to MySQL");
            e.printStackTrace();
        }
    }
}
