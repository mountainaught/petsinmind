// frontend/src/firebase.js

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "Put your own API key here from Firebase",
  authDomain: "sample-a04ec.firebaseapp.com",
  databaseURL: "https://sample-a04ec-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "sample-a04ec",
  storageBucket: "sample-a04ec.firebasestorage.app",
  messagingSenderId: "658676703211",
  appId: "1:658676703211:web:e8a47d9d477358925143ae"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
