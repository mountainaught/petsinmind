package com.petsinmind;

import com.petsinmind.firebase.FirebaseInitializer;
import com.petsinmind.firebase.FirebaseWriter;

public class Main {
    public static void main(String[] args) {
        // Initialize Firebase Admin SDK
        FirebaseInitializer.init();

        // Write a test value to /test in Firebase
        FirebaseWriter.testWrite();

        // Read the test value from /test in Firebase
        FirebaseWriter.testRead();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
