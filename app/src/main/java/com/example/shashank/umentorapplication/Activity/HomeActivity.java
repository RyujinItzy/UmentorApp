package com.example.shashank.umentorapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shashank.umentorapplication.Adapter.CourseAdapter;
import com.example.shashank.umentorapplication.Domain.CourseDomain;
import com.example.shashank.umentorapplication.R;
import com.example.shashank.umentorapplication.databinding.HomeActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityBinding binding;
    private FirebaseFirestore db;
    private RecyclerView popularRecyclerView;
    private RecyclerView recommendedRecyclerView;
    private CourseAdapter popularAdapter;
    private CourseAdapter recommendedAdapter;
    private List<CourseDomain> popularCourses;
    private List<CourseDomain> recommendedCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        popularCourses = new ArrayList<>();
        recommendedCourses = new ArrayList<>();

        popularAdapter = new CourseAdapter(popularCourses, this);
        recommendedAdapter = new CourseAdapter(recommendedCourses, this);

        popularRecyclerView = findViewById(R.id.popularView);
        recommendedRecyclerView = findViewById(R.id.recommendedViews);

        // Set layout managers for horizontal scrolling
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager recommendedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        popularRecyclerView.setLayoutManager(popularLayoutManager);
        recommendedRecyclerView.setLayoutManager(recommendedLayoutManager);

        popularRecyclerView.setAdapter(popularAdapter);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        fetchCoursesFromFirestore("courses", popularCourses, popularAdapter);
        fetchCoursesFromFirestore("courses", recommendedCourses, recommendedAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconSize(getResources().getDimensionPixelSize(R.dimen.bottom_nav_icon_size));

        // Set listener for bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    return true;
                case R.id.menu_my_courses:
                    startActivity(new Intent(HomeActivity.this, MyCoursesActivity.class));
                    return true;
                case R.id.menu_bookmarked:
                    startActivity(new Intent(HomeActivity.this, BookmarkedActivity.class));
                    return true;
                case R.id.menu_profile:
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    return true;
            }
            return false;
        });
    }

    private void fetchCoursesFromFirestore(String collection, List<CourseDomain> courses, CourseAdapter adapter) {
        db.collection(collection)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    courses.clear();
                    for (DocumentSnapshot courseDoc : queryDocumentSnapshots.getDocuments()) {
                        String title = courseDoc.getString("title");
                        String instructor = courseDoc.getString("instructor");
                        String imageUrl = courseDoc.getString("imageUrl");

                        // Use a mutable integer to store total duration
                        AtomicInteger totalDuration = new AtomicInteger(0);

                        // Fetch lessons for the current course
                        db.collection(collection).document(courseDoc.getId()).collection("lessons")
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots1 -> {
                                    for (DocumentSnapshot lessonDoc : queryDocumentSnapshots1.getDocuments()) {
                                        // Calculate total duration from lessons
                                        int duration = lessonDoc.getLong("duration").intValue();
                                        totalDuration.addAndGet(duration);
                                    }

                                    // Format total duration
                                    String formattedDuration = formatDuration(totalDuration.get());

                                    // Create CourseDomain object and add to courses list
                                    CourseDomain course = new CourseDomain(title, instructor, formattedDuration, imageUrl);
                                    courses.add(course);
                                    adapter.notifyDataSetChanged();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Error fetching lessons: " + e.getMessage());
                                    Toast.makeText(HomeActivity.this, "Failed to fetch lessons for course: " + title, Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching courses: " + e.getMessage());
                    Toast.makeText(HomeActivity.this, "Failed to fetch courses.", Toast.LENGTH_SHORT).show();
                });
    }

    private String formatDuration(int totalDuration) {
        int hours = totalDuration / 60;
        int minutes = totalDuration % 60;
        if (hours > 0) {
            return hours + " Hours " + minutes + " Minutes";
        } else {
            return minutes + " Minutes";
        }
    }
}

