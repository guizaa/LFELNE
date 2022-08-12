package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RollsActivity extends AppCompatActivity {

    private ArrayList<Roll> rolls;
    private DBHandler dbHandler;
    private RollAdapter rollAdapter;
    private int last_roll = -1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolls);
        dbHandler = new DBHandler(RollsActivity.this);
        listView = findViewById(R.id.rolls_list);

        // Add roll button
        ImageButton add_roll = findViewById(R.id.add_button);
        add_roll.setImageResource(R.drawable.add_button);
        add_roll.setOnClickListener(v ->
                startActivity(new Intent(RollsActivity.this, AddRollActivity.class)));

        // Initialize ListView
        setUpData();
        setUpList();
        setUpOnClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        int id = dbHandler.getLastIndex();

        // Check if last roll is outdated
        if (last_roll < id && id > -1) {
            // Update last roll to one past old last roll with bounds protection
            if (last_roll + 1 <= id) {
                last_roll++;
                rolls.add(dbHandler.getRoll(last_roll));
            }
            while (last_roll < id) {
                rolls.add(dbHandler.getRoll(last_roll));
                last_roll++;
            }

            // Update listview with new rolls list
            rollAdapter.updateAdapter();
        }
    }

    private void setUpData() {
        Pair<ArrayList<Roll>, Integer> return_pair = dbHandler.collectRolls();
        rolls = new ArrayList<Roll>(return_pair.first);
        last_roll = return_pair.second;
    }

    private void setUpList() {
        rollAdapter = new RollAdapter(getApplicationContext(), rolls);
        listView.setAdapter(rollAdapter);
    }

    private void setUpOnClickListener() {
        // TODO Implement
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent general_roll_activity = new Intent(RollsActivity.this, GeneralRollActivity.class);
            general_roll_activity.putExtra("roll", listView.getItemAtPosition(position).toString());
            startActivity(general_roll_activity);
        });
    }
}