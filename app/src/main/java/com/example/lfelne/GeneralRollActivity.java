package com.example.lfelne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
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
        ImageButton delete_roll_button = findViewById(R.id.delete_general_roll);
        delete_roll_button.setImageResource(R.drawable.delete_button);
        setUpDeleteListener(delete_roll_button);

        // Set text according to roll
        general_roll_name.setText(roll.getNAME());
        general_roll_type.setText(roll.getROLL_TYPE());
    }

    private void setUpDeleteListener(@NonNull ImageButton delete_roll_button) {
        delete_roll_button.setOnClickListener(v -> runDeleteAlertDialog());
    }

    private void runDeleteAlertDialog() {
        AlertDialog confirmation_dialog;
        AlertDialog.Builder confirmation_dialog_builder =
                new AlertDialog.Builder(GeneralRollActivity.this, R.style.AlertDialog);

        confirmation_dialog_builder.setTitle("Delete roll");
        confirmation_dialog_builder.setMessage("Are you sure you want to delete this roll?");

        confirmation_dialog_builder.setPositiveButton("OK", (dialog, which) -> {
            Intent confirmationIntent =
                    new Intent(GeneralRollActivity.this, RollsActivity.class);
            confirmationIntent.putExtra("deleted_roll", true);
            confirmationIntent.putExtra("roll_start_date", roll.getSTART_DATE());
            dialog.dismiss();
            setResult(Activity.RESULT_OK, confirmationIntent);
            finish();
        });

        confirmation_dialog_builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        confirmation_dialog = confirmation_dialog_builder.create();
        confirmation_dialog.show();
    }
}