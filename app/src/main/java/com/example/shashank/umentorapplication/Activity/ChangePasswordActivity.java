package com.example.shashank.umentorapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shashank.umentorapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

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
                        startActivity(new Intent(ChangePasswordActivity.this, HomeActivity.class));
                        finish();
                        return true;
                    case R.id.menu_bookmarked:
                        startActivity(new Intent(ChangePasswordActivity.this, BookmarkedActivity.class));
                        finish();
                        return true;
                    case R.id.menu_profile:
                        startActivity(new Intent(ChangePasswordActivity.this, ProfileActivity.class));
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}
