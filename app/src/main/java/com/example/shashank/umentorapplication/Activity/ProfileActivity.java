package com.example.shashank.umentorapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shashank.umentorapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconSize(getResources().getDimensionPixelSize(R.dimen.bottom_nav_icon_size));

        // Set listener for item clicks
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle bottom navigation clicks
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                        finish();
                        return true;
                    case R.id.menu_bookmarked:
                        startActivity(new Intent(ProfileActivity.this, BookmarkedActivity.class));
                        finish();
                        return true;
                    case R.id.menu_profile:
                        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Set listener for "Completed" button
        Button notificationButton = findViewById(R.id.profile_notifications);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to NotificationActivity
                startActivity(new Intent(ProfileActivity.this, NotificationActivity.class));
            }
        });

        // Set listener for "Settings" button
        Button settingsButton = findViewById(R.id.profile_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SettingsActivity
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
            }
        });

        // Set listener for "Logout" button
        Button logoutButton = findViewById(R.id.profile_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity and clear back stack
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
