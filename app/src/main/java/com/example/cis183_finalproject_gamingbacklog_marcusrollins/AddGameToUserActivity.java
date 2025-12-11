package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

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

public class AddGameToUserActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    Spinner sp_j_gameName;
    Spinner sp_j_gameStatus;
    Button btn_j_addGameToUser;
    Button btn_j_back;

    ArrayList<Integer> gameIds = new ArrayList<>();
    ArrayList<String> gameNames = new ArrayList<>();

    int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_game_to_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Database
        dbHelper = new DatabaseHelper(this);

        //Receive the userId from ProfileActivity
        currentUserId = getIntent().getIntExtra("userId", -1);
        if (currentUserId == -1)
        {
            Toast.makeText(this, "Error: No user loaded.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //GUI
        sp_j_gameName = findViewById(R.id.sp_v_addgametouser_game);
        sp_j_gameStatus = findViewById(R.id.sp_v_addgametouser_status);
        btn_j_addGameToUser = findViewById(R.id.btn_v_addgametouser_add);
        btn_j_back = findViewById(R.id.btn_v_addgametouser_back);

        loadGamesIntoSpinner();
        setupStatusSpinner();
        buttonCallListener();
    }

    //Load all games from the DB into the spinner
    private void loadGamesIntoSpinner()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT gameId, gameName FROM Games", null);

        gameIds.clear();
        gameNames.clear();

        while (cursor.moveToNext()) {
            gameIds.add(cursor.getInt(0));
            gameNames.add(cursor.getString(1));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gameNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_j_gameName.setAdapter(adapter);
    }

    private void setupStatusSpinner()
    {
        ArrayList<String> statuses = new ArrayList<>();
        statuses.add("main");
        statuses.add("backlogged");
        statuses.add("completed");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_j_gameStatus.setAdapter(adapter);
    }

    private void buttonCallListener()
    {
        //Add Game to User Profile
        btn_j_addGameToUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedGameId = gameIds.get(sp_j_gameName.getSelectedItemPosition());
                String selectedStatus = sp_j_gameStatus.getSelectedItem().toString();

                //If the game is marked as complete, give the user 100 xp automatically
                int xp = selectedStatus.equals("completed") ? 100 : 0;

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.execSQL("INSERT INTO UserGames (userId, gameId, status, earnedXp) VALUES (?, ?, ?, ?)", new Object[]{currentUserId, selectedGameId, selectedStatus, xp});

                Toast.makeText(AddGameToUserActivity.this, "Game added to your profile!", Toast.LENGTH_SHORT).show();
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