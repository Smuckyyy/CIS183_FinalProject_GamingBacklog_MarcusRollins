package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddGameDatabaseActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    EditText et_j_gameName;
    Spinner sp_j_systemName;
    Button btn_j_addGame;
    Button btn_j_back;

    ArrayList<Integer> systemIds = new ArrayList<>(); //Storing system names for use with the spinner

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_game_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Database
        dbHelper = new DatabaseHelper(this);

        //GUI
        et_j_gameName = findViewById(R.id.et_v_addgamedb_gameName);
        sp_j_systemName = findViewById(R.id.sp_v_addgamedb_systemName);
        btn_j_addGame = findViewById(R.id.btn_v_addgamedb_addgame);
        btn_j_back = findViewById(R.id.btn_v_addgamedb_back);

        loadSystemsInSpinner();

        buttonCallListener();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadSystemsInSpinner();
    }

    private void loadSystemsInSpinner()
    {
        Cursor cursor = dbHelper.getAllSystems();

        ArrayList<String> systemNames = new ArrayList<>();
        systemIds.clear();

        while(cursor.moveToNext())
        {
            systemIds.add(cursor.getInt(0)); //ID
            systemNames.add(cursor.getString(1)); //Name
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, systemNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_j_systemName.setAdapter(adapter);
    }

    private void buttonCallListener()
    {
        //Adding a game
        btn_j_addGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String gameName = et_j_gameName.getText().toString();
                int systemId = systemIds.get(sp_j_systemName.getSelectedItemPosition()); //from the spinner

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String sql = "INSERT INTO Games (gameName, systemId) VALUES (?, ?)";
                db.execSQL(sql, new Object[]{gameName, systemId});

                Toast.makeText(AddGameDatabaseActivity.this, "Game Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Back
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