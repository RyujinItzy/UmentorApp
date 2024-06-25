package com.example.shashank.umentorapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shashank.umentorapplication.Adapter.EnrolledCourseAdapter;
import com.example.shashank.umentorapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyCoursesActivity extends AppCompatActivity {

    private RecyclerView enrolledCoursesRecyclerView;
    private EnrolledCourseAdapter enrolledCourseAdapter;
    private List<Map<String, Object>> enrolledCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_courses);

        // Initialize RecyclerView and Adapter
        enrolledCoursesRecyclerView = findViewById(R.id.enrolledCoursesRecyclerView);
        enrolledCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        enrolledCourses = new ArrayList<>();
        enrolledCourseAdapter = new EnrolledCourseAdapter(enrolledCourses);
        enrolledCoursesRecyclerView.setAdapter(enrolledCourseAdapter);

        // Fetch enrolled courses from Firestore
        fetchEnrolledCourses();

        // Setup bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    startActivity(new Intent(MyCoursesActivity.this, HomeActivity.class));
                    return true;
                case R.id.menu_bookmarked:
                    startActivity(new Intent(MyCoursesActivity.this, BookmarkedActivity.class));
                    return true;
                case R.id.menu_profile:
                    startActivity(new Intent(MyCoursesActivity.this, ProfileActivity.class));
                    return true;
                default:
                    return false;
            }
        });

        // Set up the back button
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void fetchEnrolledCourses() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Log.e("MyCoursesActivity", "User not authenticated");
            startActivity(new Intent(MyCoursesActivity.this, StartActivity.class));
            finish();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> enrolledCourseIds = (List<String>) documentSnapshot.get("enrolledCourses");
                        if (enrolledCourseIds != null) {
                            fetchCourseDetails(enrolledCourseIds);
                        }
                    } else {
                        Log.e("MyCoursesActivity", "No document found for user");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MyCoursesActivity", "Error fetching user document: ", e);
                });
    }

    private void fetchCourseDetails(List<String> courseIds) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (String courseId : courseIds) {
            db.collection("Courses").document(courseId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> course = documentSnapshot.getData();
                            if (course != null) {
                                // Fetch lesson count
                                fetchLessonCount(courseId, course);
                            }
                        } else {
                            Log.e("MyCoursesActivity", "No document found for course ID: " + courseId);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MyCoursesActivity", "Error fetching course document: ", e);
                    });
        }
    }

    private void fetchLessonCount(String courseId, Map<String, Object> course) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Courses").document(courseId).collection("lessons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int lessonCount = queryDocumentSnapshots.size();
                    course.put("lessonCount", lessonCount);
                    enrolledCourses.add(course);
                    enrolledCourseAdapter.setEnrolledCourses(enrolledCourses);
                })
                .addOnFailureListener(e -> {
                    Log.e("MyCoursesActivity", "Error fetching lessons for course ID: " + courseId, e);
                });
    }
}