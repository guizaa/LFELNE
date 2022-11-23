package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class RollInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_info);

        String roll_init = getIntent().getStringExtra("roll");
        Roll roll = new Roll(roll_init);

        ImageButton ecks_button = findViewById(R.id.ecks_button);
        TextView name = findViewById(R.id.roll_info_name);
        TextView type = findViewById(R.id.roll_info_type);
        TextView exposures = findViewById(R.id.roll_info_exposures);
        TextView date = findViewById(R.id.roll_info_date);

        name.setText(roll.getNAME());
        type.setText(roll.getROLL_TYPE());
        exposures.setText(String.valueOf(roll.getMAX_EXPOSURES()));
        date.setText(roll.getStringSTART_DATE());

        ecks_button.setImageResource(R.drawable.ecks_button);
        ecks_button.setOnClickListener(v -> finish());



    }
}