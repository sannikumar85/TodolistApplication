package com.todolistapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupToolbar();
        setupButtons();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("About");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupButtons() {
        Button btnRateApp = findViewById(R.id.btnRateApp);
        Button btnShareApp = findViewById(R.id.btnShareApp);
        Button btnFeedback = findViewById(R.id.btnFeedback);

        btnRateApp.setOnClickListener(v -> openPlayStore());
        btnShareApp.setOnClickListener(v -> shareApp());
        btnFeedback.setOnClickListener(v -> sendFeedback());
    }

    private void openPlayStore() {
        // In a real app, use your actual package name
        String packageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this awesome Todo List app!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using this amazing Todo List app to stay organized. You should try it too! 📝✨");
        startActivity(Intent.createChooser(shareIntent, "Share App"));
    }

    private void sendFeedback() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:developer@todolistapp.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Todo List App Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi! I'd like to share some feedback about the Todo List app...");
        
        try {
            startActivity(Intent.createChooser(emailIntent, "Send Feedback"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle case where no email app is available
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}