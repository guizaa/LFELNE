package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RollSQLHandler dbHandler;
    ArrayList<Roll> rolls;
    RollAdapter adapter;
    Roll selected_roll;
    Spinner spinna;
    ImageButton take_photo_button;
    TextView exposures_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dbHandler = new RollSQLHandler(MainActivity.this);
        rolls = new ArrayList<>(dbHandler.collectNonFullRolls());
        adapter = new RollAdapter(getApplicationContext(), rolls);

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

        // Sets up take photo button
        take_photo_button = findViewById(R.id.take_photo_button);
        take_photo_button.setImageResource(R.drawable.take_photo_button);

        // Sets up dropdown menu
        spinna = findViewById(R.id.dropdown);
        spinna.setAdapter(adapter);

        exposures_count = findViewById(R.id.exposures_count);

        setUpTakePhotoListener();
        setUpSpinnerSelectListener();
        updateExposuresCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSelectedRoll();
    }

    private void updateSelectedRoll() {
        ArrayList<Roll> new_rolls = dbHandler.collectNonFullRolls();
        rolls.clear();
        if (!new_rolls.isEmpty()) {
            rolls.addAll(new_rolls);
        }
        adapter.updateAdapter();
        updateExposuresCount();
    }

    private void updateExposuresCount() {
        // Sets up exposures count
        if (selected_roll == null)
            exposures_count.setText("");
        else {
            String exposures_count_string = selected_roll.getExposures() + "/" +
                    selected_roll.getMAX_EXPOSURES();
            exposures_count.setText(exposures_count_string);
        }
    }

    private void setUpTakePhotoListener() {
        take_photo_button.setOnClickListener(v -> {
            if (selected_roll != null &&
                selected_roll.getExposures() < selected_roll.getMAX_EXPOSURES()) {
                dbHandler.incrementExposures(selected_roll.getSTART_DATE(),
                        selected_roll.getExposures());
                selected_roll.incrementExposures();
                updateExposuresCount();
            }
        });
    }

    private void setUpSpinnerSelectListener() {
        spinna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                selected_roll = rolls.get(position);
                updateExposuresCount();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                if (!rolls.isEmpty())
                    selected_roll = rolls.get(0);
                else selected_roll = null;
                updateExposuresCount();
            }
        });
    }
}