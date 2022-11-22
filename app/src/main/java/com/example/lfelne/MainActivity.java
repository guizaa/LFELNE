package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Open rolls activity
        Button openRolls = findViewById(R.id.rolls_button);
        openRolls.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RollsActivity.class)));

        // Open stats activity
        Button openStats = findViewById(R.id.stats_button);
        openStats.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, StatsActivity.class)));

        // Open map activity
        Button openMap = findViewById(R.id.map_button);
        openMap.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MapActivity.class)));

        // Open settings activity
        Button openSettings = findViewById(R.id.settings_button);
        openSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }
}