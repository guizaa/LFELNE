package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class AddRollActivity extends AppCompatActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roll);
        dbHandler = new DBHandler(AddRollActivity.this);

        Button add_new_roll_button = (Button) findViewById(R.id.add_new_roll_button);
        EditText roll_name = (EditText) findViewById(R.id.roll_name_edit);
        EditText roll_type = (EditText) findViewById(R.id.roll_type_edit);
        EditText roll_exposures = (EditText) findViewById(R.id.roll_exposures_edit);
        Date start_date = new Date();

        add_new_roll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String init_roll = roll_name.getText() + "-" + roll_type.getText() + "-" +
                        roll_exposures.getText() + "-0-" + start_date.getTime() + "-" +
                        start_date.getTime();
                Roll roll = new Roll(init_roll);
                dbHandler.addNewRoll(roll);

                finish();
            }
        });
    }
}