package com.example.shashank.umentorapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shashank.umentorapplication.Domain.Lesson;
import com.example.shashank.umentorapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDetailActivity extends AppCompatActivity {
private TextView courseTitleTextView;
private TextView courseOwnerTextView;
private TextView coursePriceTextView;
private ImageView courseImageView;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);


        // Retrieve intent extras
        Intent intent = getIntent();
        if (intent != null) {
        String courseTitle = intent.getStringExtra("courseTitle");
        String courseOwner = intent.getStringExtra("courseOwner");
        String courseDuration = intent.getStringExtra("courseDuration");
        String courseImage = intent.getStringExtra("courseImage");

        // Set data to views
        TextView titleTextView = findViewById(R.id.courseTitleTxt);
        TextView ownerTextView = findViewById(R.id.courseInstructorTxt);
        TextView durationTextView = findViewById(R.id.courseDurationTxt);
        ImageView imageView = findViewById(R.id.courseImage);

        titleTextView.setText(courseTitle);
        ownerTextView.setText(courseOwner);
        durationTextView.setText(courseDuration);

        // Load image dynamically
        Glide.with(this).load(courseImage).into(imageView);
        }
        }
private void enrollCourse() {
        // Get the current user's ID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
        // Handle the case where user is not authenticated
        Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        return;
        }

        // Get course ID and details from intent
        String courseId = getIntent().getStringExtra("courseId");
        String courseTitle = getIntent().getStringExtra("courseTitle");

        // Update enrolledCourses array in Firestore for the current user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(currentUser.getUid());

        // Check if the course is already enrolled
        userRef.get().addOnSuccessListener(documentSnapshot -> {
        if (documentSnapshot.exists()) {
        List<String> enrolledCourses = (List<String>) documentSnapshot.get("enrolledCourses");

        // Check if the course already exists in enrolledCourses
        if (enrolledCourses != null && enrolledCourses.contains(courseTitle)) {
        Toast.makeText(this, "You are already enrolled in this course", Toast.LENGTH_SHORT).show();
        } else {
        // Update enrolledCourses array with the new course title
        userRef.update("enrolledCourses", FieldValue.arrayUnion(courseTitle))
        .addOnSuccessListener(aVoid -> {
        Toast.makeText(this, "Enrolled in course successfully", Toast.LENGTH_SHORT).show();
        // Handle UI update or navigation if needed after enrollment
        })
        .addOnFailureListener(e -> {
        Log.e("Firestore", "Error enrolling in course: " + e.getMessage());
        Toast.makeText(this, "Failed to enroll in course", Toast.LENGTH_SHORT).show();
        });
        }
        }
        }).addOnFailureListener(e -> {
        Log.e("Firestore", "Error fetching user data: " + e.getMessage());
        Toast.makeText(this, "Failed to check enrollment status", Toast.LENGTH_SHORT).show();
        });
}
}