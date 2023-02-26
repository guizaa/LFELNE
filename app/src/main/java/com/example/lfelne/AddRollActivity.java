package com.example.lfelne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class AddRollActivity extends AppCompatActivity {
    private RollSQLHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roll);
        dbHandler = new RollSQLHandler(AddRollActivity.this);

        Button add_new_roll_button = findViewById(R.id.add_new_roll_button);
        EditText roll_name = findViewById(R.id.roll_name_edit);
        EditText roll_type = findViewById(R.id.roll_type_edit);
        EditText roll_exposures = findViewById(R.id.roll_exposures_edit);
        Date start_date = new Date();

        add_new_roll_button.setOnClickListener(v -> {
            try {
                if (Short.parseShort(String.valueOf(roll_exposures.getText())) > 256 ||
                        Short.parseShort(String.valueOf(roll_exposures.getText())) < 0) {
                    roll_exposures.setError("Enter a number between 0 and 256");
                }
                if (roll_name.getText().toString().length() > 15) {
                    roll_name.setError("Enter a name less than 15 characters");
                }
                else if (roll_name.getText().length() != 0 &&
                        roll_type.getText().length() != 0 &&
                        roll_exposures.getText().length() != 0) {
                    String init_roll = roll_name.getText() + "\n" + roll_type.getText() + "\n" +
                            roll_exposures.getText() + "\n0\n" + start_date.getTime() + "\n" +
                            start_date.getTime();
                    Roll roll = new Roll(init_roll);
                    dbHandler.addNewRoll(roll);

                    finish();
                }
            }
            catch (NumberFormatException | ArithmeticException e) {
                roll_exposures.setError("Enter a number between 0 and 256");
            }
        });
    }
}