package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class RemoveSystemActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    ListView lv_j_systems;
    ArrayList<String> systemsList;
    ArrayAdapter<String> adapter;
    Button btn_j_back;
    int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_system);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentUserId = getIntent().getIntExtra("userId", -1);

        dbHelper = new DatabaseHelper(this);
        lv_j_systems = findViewById(R.id.lv_v_removesystem_listOfSystems);
        btn_j_back = findViewById(R.id.btn_v_removesystem_back);

        systemsList = new ArrayList<>();

        loadSystems();
        setupListClick();
        buttonClickListener();
    }

    //Load all of the users systems and load them into the listview
    private void loadSystems()
    {
        systemsList.clear();

        Cursor cursor = dbHelper.getSystemsForUser(currentUserId);
        if (cursor.moveToFirst()) {
            do
            {
                systemsList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, systemsList);

        lv_j_systems.setAdapter(adapter);
    }

    private void setupListClick()
    {
        lv_j_systems.setOnItemLongClickListener((parent, view, position, id) -> {

            String systemName = systemsList.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Remove System")
                    .setMessage("Remove " + systemName + " and it's games from your profile?")
                    .setPositiveButton("Remove", (dialog, which) ->
                    {

                        dbHelper.removeSystemFromUser(currentUserId, systemName);
                        loadSystems(); //Refresh the list
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }

    private void buttonClickListener()
    {
        btn_j_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}