package com.todolistapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvTotalTodos, tvCompletedTodos, tvPendingTodos, tvCompletionRate;
    private TextView tvTodayTodos, tvWeeklyTodos, tvMonthlyTodos;
    private TextView tvProductivityScore, tvStreakDays, tvCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();
        setupToolbar();
        loadStatistics();
    }

    private void initViews() {
        tvTotalTodos = findViewById(R.id.tvTotalTodos);
        tvCompletedTodos = findViewById(R.id.tvCompletedTodos);
        tvPendingTodos = findViewById(R.id.tvPendingTodos);
        tvCompletionRate = findViewById(R.id.tvCompletionRate);
        tvTodayTodos = findViewById(R.id.tvTodayTodos);
        tvWeeklyTodos = findViewById(R.id.tvWeeklyTodos);
        tvMonthlyTodos = findViewById(R.id.tvMonthlyTodos);
        tvProductivityScore = findViewById(R.id.tvProductivityScore);
        tvStreakDays = findViewById(R.id.tvStreakDays);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Statistics Dashboard");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadStatistics() {
        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
        tvCurrentDate.setText(sdf.format(new Date()));

        // Sample statistics (in real app, load from database)
        tvTotalTodos.setText("47");
        tvCompletedTodos.setText("32");
        tvPendingTodos.setText("15");
        tvCompletionRate.setText("68%");
        tvTodayTodos.setText("8");
        tvWeeklyTodos.setText("23");
        tvMonthlyTodos.setText("47");
        tvProductivityScore.setText("85");
        tvStreakDays.setText("12");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}