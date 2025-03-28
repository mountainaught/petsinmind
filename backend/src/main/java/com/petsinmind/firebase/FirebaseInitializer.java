package com.petsinmind.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.InputStream;
import java.io.IOException;
/**
 * This class initializes Firebase with the service account key, keep the key in the resources file and dont push it to the repository
 * 
 * it should be called once in the start of the application i.e Main.java
 * 
 * it loads the servce account key from the resources file and initializes the Firebase app i.e connecting to the Firebase database
 * 
 * 
 * 
 */





public class FirebaseInitializer {

    /*
     * This method initializes the Firebase app with the service account key before read/write operations
     */
    public static void init() {
        try {

            // Load the service account key from the resources file
            InputStream serviceAccount = FirebaseInitializer.class
                .getClassLoader()
                .getResourceAsStream("petsinmindkey.json");

            if (serviceAccount == null) {
                System.err.println(" petsinmindkey.json not found in classpath.");
                return;
            }
            //configures firebase with database url
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://sample-a04ec-default-rtdb.europe-west1.firebasedatabase.app")
                .build();

            FirebaseApp.initializeApp(options);
            System.out.println(" Firebase initialized");

        } catch (IOException e) {
            System.err.println(" Failed to initialize Firebase: " + e.getMessage());
        }
    }
}
