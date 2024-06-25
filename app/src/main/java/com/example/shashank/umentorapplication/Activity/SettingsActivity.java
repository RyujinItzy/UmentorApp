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

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

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
                        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                        finish();
                        return true;
                    case R.id.menu_bookmarked:
                        startActivity(new Intent(SettingsActivity.this, BookmarkedActivity.class));
                        finish();
                        return true;
                    case R.id.menu_profile:
                        startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });
        Button changePassButton = findViewById(R.id.change_pass);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SettingsActivity
                startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
            }
        });
    }
}

