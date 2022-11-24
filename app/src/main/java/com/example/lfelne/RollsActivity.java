package com.example.lfelne;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class RollsActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    ListView listView;
    ArrayList<Roll> rolls;
    RollAdapter rollAdapter;
    ActivityResultLauncher<Intent> generalRollLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    if (data.getBooleanExtra("deleted_roll", false))
                        dbHandler.removeRoll(data.getLongExtra("roll_start_date",
                                -1L));
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolls);
        dbHandler = new DBHandler(RollsActivity.this);
        listView = findViewById(R.id.rolls_list);

        // Add roll button
        ImageButton add_roll_button = findViewById(R.id.add_button);
        add_roll_button.setImageResource(R.drawable.add_button);
        add_roll_button.setOnClickListener(v ->
                startActivity(new Intent(RollsActivity.this, AddRollActivity.class)));

        // Add search bar
        EditText searchbar = findViewById(R.id.rolls_list_searchbar);
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                rollAdapter.getFilter().filter(editable.toString());
            }
        });

        // Initialize ListView
        setUpList();
        setUpOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rolls.clear();
        rolls.addAll(dbHandler.collectRolls());
        rollAdapter.updateAdapter();
    }

    private void setUpList() {
        rolls = dbHandler.collectRolls();
        rollAdapter = new RollAdapter(getApplicationContext(), rolls);
        listView.setAdapter(rollAdapter);
    }

    private void setUpOnClickListener() {
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent general_roll_activity = new Intent(RollsActivity.this, GeneralRollActivity.class);
            general_roll_activity.putExtra("roll", listView.getItemAtPosition(position).toString());
            startGeneralRollForResult(general_roll_activity);
        });
    }

    private void startGeneralRollForResult(Intent general_roll_activity) {
        generalRollLauncher.launch(general_roll_activity);
    }
}