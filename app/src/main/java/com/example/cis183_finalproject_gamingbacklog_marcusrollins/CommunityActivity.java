package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    Button btn_j_users;
    Button btn_j_systems;
    Button btn_j_topGames;
    Button btn_j_gamesLogged;
    Button btn_j_gameXP;
    ListView lv_j_updateLV;
    TextView tabProfile, tabGames, tabCommunity;
    int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_community);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Get current userId from intent
        currentUserId = getIntent().getIntExtra("userId", -1);

        //Database
        dbHelper = new DatabaseHelper(this);

        //Bottom tab references
        tabProfile = findViewById(R.id.tab_profile);
        tabGames = findViewById(R.id.tab_games);
        tabCommunity = findViewById(R.id.tab_community);

        //GUI
        btn_j_users = findViewById(R.id.btn_v_community_users);
        btn_j_systems = findViewById(R.id.btn_v_community_systems);
        btn_j_gameXP = findViewById(R.id.btn_v_community_gameXP);
        btn_j_gamesLogged = findViewById(R.id.btn_v_community_gamesLogged);
        btn_j_topGames = findViewById(R.id.btn_v_community_popularGames);
        lv_j_updateLV = findViewById(R.id.lv_v_community_listOfUsers);

        buttonCallListener();
        setupBottomTabs();
    }



    private void setupBottomTabs()
    {
        tabProfile.setOnClickListener(v ->
        {
            Intent intent = new Intent(CommunityActivity.this, ProfileActivity.class);
            intent.putExtra("userId", currentUserId);
            startActivity(intent);
        });

        tabGames.setOnClickListener(v ->
        {
            Intent intent = new Intent(CommunityActivity.this, GameViewerActivity.class);
            intent.putExtra("userId", currentUserId);
            startActivity(intent);
        });

        tabCommunity.setOnClickListener(v ->
        {
            //Nothing goes here since we're already on Community
        });
    }

    private void buttonCallListener()
    {
        btn_j_users.setOnClickListener(v -> loadUsernames());
        btn_j_systems.setOnClickListener(v -> loadSystems());
        btn_j_topGames.setOnClickListener(v -> loadTopGames());
        btn_j_gamesLogged.setOnClickListener(v -> loadGamesLogged());
        btn_j_gameXP.setOnClickListener(v -> loadGameXpTotals());
    }

    //Need to fill out the queries using cursors with the database, then run to check if community is working
    private void loadUsernames()
    {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = dbHelper.getAllUsernames();

        while(cursor.moveToNext())
        {
            data.add(cursor.getString(0)); //Usernames
        }
        cursor.close();
        updateListView(data);
    }

    private void loadSystems()
    {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = dbHelper.getAllSystemsCommunity();

        while(cursor.moveToNext())
        {
            data.add(cursor.getString(0)); //System name
        }

        cursor.close();
        updateListView(data);
    }

    private void loadTopGames()
    {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = dbHelper.getTopGamesPlayed();

        while (cursor.moveToNext())
        {
            String game = cursor.getString(0);
            int times = cursor.getInt(1);
            data.add(game + " — played " + times + " times");
        }
        cursor.close();

        updateListView(data);
    }

    private void loadGamesLogged()
    {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = dbHelper.getGamesLogged();

        while (cursor.moveToNext()) //All games logged
        {
            String username = cursor.getString(0);
            int count = cursor.getInt(1);
            data.add(username + ": " + count + " games logged");
        }
        cursor.close();

        updateListView(data);
    }

    private void loadGameXpTotals()
    {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = dbHelper.getGameXpTotals();

        while (cursor.moveToNext())
        {
            String game = cursor.getString(0);
            int xp = cursor.getInt(1);
            data.add(game + ": " + xp + " XP earned total");
        }
        cursor.close();

        updateListView(data);
    }

    private void updateListView(ArrayList<String> data)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        lv_j_updateLV.setAdapter(adapter);
    }
}