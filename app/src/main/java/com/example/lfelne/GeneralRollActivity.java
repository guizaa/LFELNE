package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GeneralRollActivity extends AppCompatActivity {
    Roll roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_roll);
        roll = new Roll(getIntent().getStringExtra("roll"));

        // Gather TextView objects
        TextView general_roll_name = findViewById(R.id.general_roll_name);
        TextView general_roll_type = findViewById(R.id.general_roll_type);

        // Set text according to roll
        general_roll_name.setText(roll.getNAME());
        general_roll_type.setText(roll.getROLL_TYPE());
    }


}