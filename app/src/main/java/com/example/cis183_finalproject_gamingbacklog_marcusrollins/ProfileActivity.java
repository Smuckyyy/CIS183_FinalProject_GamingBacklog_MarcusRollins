package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity
{
    Button btn_j_addGame;
    Button btn_j_addSystem;
    TextView tabProfile, tabGames, tabCommunity;
    int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentUserId = getIntent().getIntExtra("userId", -1);

        //GUI
        btn_j_addGame = findViewById(R.id.btn_v_profile_addGame);
        btn_j_addSystem = findViewById(R.id.btn_v_profile_addSystem);

        //Bottom tab references
        tabProfile = findViewById(R.id.tab_profile);
        tabGames = findViewById(R.id.tab_games);
        tabCommunity = findViewById(R.id.tab_community);

        setupBottomTabs();
        buttonCallListener();

    }

    private void setupBottomTabs()
    {
        tabProfile.setOnClickListener(v ->
        {
            //Nothing goes here since we're already on the Profile
        });

        tabGames.setOnClickListener(v ->
        {
            Intent intent = new Intent(ProfileActivity.this, GameViewerActivity.class);
            intent.putExtra("userId", currentUserId);
            startActivity(intent);
        });

        tabCommunity.setOnClickListener(v ->
        {
            Intent intent = new Intent(ProfileActivity.this, CommunityActivity.class);
            intent.putExtra("userId", currentUserId);
            startActivity(intent);
        });
    }

    private void buttonCallListener()
    {
        //Add Game
        btn_j_addGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addGameIntent = new Intent(ProfileActivity.this, AddGameDatabaseActivity.class);
                startActivity(addGameIntent);
            }
        });

        //Add System
        btn_j_addSystem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addSystemIntent = new Intent(ProfileActivity.this, AddSystemDatabaseActivity.class);
                startActivity(addSystemIntent);
            }
        });
    }
}