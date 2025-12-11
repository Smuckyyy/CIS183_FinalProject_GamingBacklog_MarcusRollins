package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CommunityActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    Button btn_j_users;
    Button btn_j_systems;
    Button btn_j_topGames;
    Button btn_j_gamesLogged;
    Button btn_j_gameXP;
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

        setupBottomTabs();
        buttonCallListener();
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

    }

    private void loadSystems()
    {

    }

    private void loadTopGames()
    {

    }

    private void loadGamesLogged()
    {

    }

    private void loadGameXpTotals()
    {

    }
}