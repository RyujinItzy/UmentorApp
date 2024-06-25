package com.example.shashank.umentorapplication;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyApp extends Application {
    public static FirebaseAuth mAuth;
    public static FirebaseFirestore db;
    public static FirebaseStorage storage;
    public static StorageReference storageRef;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase Auth and Firestore
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }
}
