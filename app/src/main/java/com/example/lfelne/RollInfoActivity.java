package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class RollInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_info);

        ImageButton ecks_button = findViewById(R.id.ecks_button);
        ecks_button.setImageResource(R.drawable.ecks_button);
        ecks_button.setOnClickListener(v -> finish());

    }
}