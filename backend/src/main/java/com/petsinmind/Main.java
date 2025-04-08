package com.petsinmind;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.UUID;

import com.petsinmind.users.Caretaker;
import com.petsinmind.utils.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("DB URL: " + Config.get("db.url"));
        try {
            Connection conn = DriverManager.getConnection(
                Config.get("db.url"),
                Config.get("db.user"),
                Config.get("db.password")
            );

            System.out.println("✅ Connected to MySQL");

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

            // 🔸 Insert
            DatabaseManager.insertCaretaker(conn, ct);

            // 🔸 Show updated list
            DatabaseManager.readCaretakers(conn);

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to connect to MySQL");
            e.printStackTrace();
        }
    }
}
