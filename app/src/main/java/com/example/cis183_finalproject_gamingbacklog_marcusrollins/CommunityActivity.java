package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CommunityActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
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
}