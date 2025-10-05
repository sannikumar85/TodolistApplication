package com.todolistapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchNotifications, switchDarkMode, switchAutoBackup, switchReminders;
    private SharedPreferences preferences;
    private static final String PREFS_NAME = "TodoListSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupToolbar();
        initViews();
        loadPreferences();
        setupListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchAutoBackup = findViewById(R.id.switchAutoBackup);
        switchReminders = findViewById(R.id.switchReminders);
        
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    private void loadPreferences() {
        switchNotifications.setChecked(preferences.getBoolean("notifications", true));
        switchDarkMode.setChecked(preferences.getBoolean("dark_mode", false));
        switchAutoBackup.setChecked(preferences.getBoolean("auto_backup", true));
        switchReminders.setChecked(preferences.getBoolean("reminders", true));
    }

    private void setupListeners() {
        switchNotifications.setOnCheckedChangeListener(createPreferenceListener("notifications"));
        switchDarkMode.setOnCheckedChangeListener(createPreferenceListener("dark_mode"));
        switchAutoBackup.setOnCheckedChangeListener(createPreferenceListener("auto_backup"));
        switchReminders.setOnCheckedChangeListener(createPreferenceListener("reminders"));
    }

    private CompoundButton.OnCheckedChangeListener createPreferenceListener(String key) {
        return (buttonView, isChecked) -> {
            preferences.edit().putBoolean(key, isChecked).apply();
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}