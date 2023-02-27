package com.example.lfelne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GeneralRollActivity extends AppCompatActivity {
    Roll roll;
    ArrayList<ImageItem> images = new ArrayList<>();
    ImageAdapter imageAdapter;
    RollSQLHandler rollSQLHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_roll);
        roll = new Roll(getIntent().getStringExtra("roll"));
        rollSQLHandler = new RollSQLHandler(GeneralRollActivity.this);

        // Gather objects
        TextView general_roll_name = findViewById(R.id.general_roll_name);
        TextView general_roll_type = findViewById(R.id.general_roll_type);
        ImageButton delete_roll_button = findViewById(R.id.delete_roll);
        ListView image_list = findViewById(R.id.general_roll_photos);

        delete_roll_button.setImageResource(R.drawable.delete_button);
        setUpDeleteListener(delete_roll_button);
        setUpInfoListener(general_roll_name);
        setUpImageList(image_list);
        setUpOnGeneralImageClickListener(image_list);

        // Set text according to roll
        general_roll_name.setText(roll.getNAME());
        general_roll_type.setText(roll.getROLL_TYPE());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpImages();
        imageAdapter.notifyDataSetChanged();
    }

    private void setUpInfoListener(@NonNull TextView info_button) {
        Intent intent = new Intent(GeneralRollActivity.this, RollInfoActivity.class);
        intent.putExtra("roll", roll.toString());
        info_button.setOnClickListener(v ->
                startActivity(intent));
    }

    private void setUpDeleteListener(@NonNull ImageButton delete_roll_button) {
        delete_roll_button.setOnClickListener(v -> runDeleteAlertDialog());
    }

    private void runDeleteAlertDialog() {
        AlertDialog confirmation_dialog;
        AlertDialog.Builder confirmation_dialog_builder =
                new AlertDialog.Builder(GeneralRollActivity.this, R.style.AlertDialog);

        confirmation_dialog_builder.setTitle("Delete " + roll.getNAME());
        confirmation_dialog_builder.setMessage("Are you sure you want to delete roll "
                + roll.getNAME() +"?");

        confirmation_dialog_builder.setPositiveButton("OK", (dialog, which) -> {
            Intent confirmationIntent =
                    new Intent(GeneralRollActivity.this, RollsActivity.class);
            confirmationIntent.putExtra("deleted_roll", true);
            confirmationIntent.putExtra("roll_start_date", roll.getSTART_DATE());
            dialog.dismiss();
            setResult(Activity.RESULT_OK, confirmationIntent);
            finish();
        });

        confirmation_dialog_builder.setNegativeButton("Cancel",
                (dialog, which) -> dialog.dismiss());

        confirmation_dialog = confirmation_dialog_builder.create();
        confirmation_dialog.show();
    }

    private void setUpImages() {
        images.clear();
        String[] uris = rollSQLHandler.getImageUris(roll.getSTART_DATE()).split(",");
        for (int i = 0; i < roll.getExposures(); i++) {
            String[] image_item_init = uris[i].split("%");
            if (Objects.equals(image_item_init[0], "n")) // if image is blank
                images.add(new ImageItem(null, Long.parseLong(image_item_init[1])));
            else images.add(new ImageItem(Uri.parse(image_item_init[0]),
                    Long.parseLong(image_item_init[1])));

        }
    }
    private void setUpImageList(@NonNull ListView image_list) {
        setUpImages();
        imageAdapter = new ImageAdapter(getApplicationContext(), images);
        image_list.setAdapter(imageAdapter);
    }

    private void setUpOnGeneralImageClickListener(@NonNull ListView image_list) {
        image_list.setOnItemClickListener((adapterView, view, position, id) -> {
            // TODO: IMPLEMENT
        });
    }
}