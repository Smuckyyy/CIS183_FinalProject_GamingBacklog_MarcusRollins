package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    TextView tv_j_username;
    TextView tv_j_totalXp;
    TextView tv_j_systems;
    Button btn_j_addGameToProfile;
    Button btn_j_addGameToDB;
    Button btn_j_addSystemToDB;
    Button btn_j_logout;
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

        //Database
        dbHelper = new DatabaseHelper(this);

        currentUserId = getIntent().getIntExtra("userId", -1);

        //This will ensure that the username of the current user is shown on the profile with the help of the DB
        tv_j_username = findViewById(R.id.tv_v_profile_username);
        String name = dbHelper.getUsernameById(currentUserId);
        if(name != null)
        {
            tv_j_username.setText(name);
        }
        else
        {
            tv_j_username.setText("Gamer");
        }

        tv_j_totalXp = findViewById(R.id.tv_v_profile_totalXP);
        tv_j_systems = findViewById(R.id.tv_v_profile_systems);

        //GUI
        btn_j_addGameToProfile = findViewById(R.id.btn_v_profile_addGameToProfile);
        btn_j_addGameToDB = findViewById(R.id.btn_v_profile_addGameToDB);
        btn_j_addSystemToDB = findViewById(R.id.btn_v_profile_addSystemToDB);
        btn_j_logout = findViewById(R.id.btn_v_profile_logout);

        //Bottom tab references
        tabProfile = findViewById(R.id.tab_profile);
        tabGames = findViewById(R.id.tab_games);
        tabCommunity = findViewById(R.id.tab_community);


        loadUserProfile();
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

    private void loadUserProfile()
    {
        //Load total XP
        int totalXp = dbHelper.getTotalXpForUser(currentUserId);
        tv_j_totalXp.setText(String.valueOf(totalXp));

        //Load systems
        Cursor cursor = dbHelper.getSystemsForUser(currentUserId);

        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext())
        {
            sb.append(cursor.getString(0)).append("\n");
        }
        cursor.close();

        if (sb.length() == 0)
        {
            sb.append("No systems added yet.");
        }

        tv_j_systems.setText(sb.toString());
    }

    private void buttonCallListener()
    {
        //Add Game
        btn_j_addGameToDB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addGameIntent = new Intent(ProfileActivity.this, AddGameDatabaseActivity.class);
                startActivity(addGameIntent);
            }
        });

        //Add System
        btn_j_addSystemToDB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addSystemIntent = new Intent(ProfileActivity.this, AddSystemDatabaseActivity.class);
                startActivity(addSystemIntent);
            }
        });

        //Add Game to Profile
        btn_j_addGameToProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addGameToProfileIntent = new Intent(ProfileActivity.this, AddGameToUserActivity.class);
                addGameToProfileIntent.putExtra("userId", getIntent().getIntExtra("userId", -1));
                startActivity(addGameToProfileIntent);
            }
        });

        //Logout
        btn_j_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                //What flag activity clear top does is not allow the user to use the android feature of sliding the edge of the screen to the center to go back if they choose to logout.
                //This makes it so the userId doesn't get messed up if they "accidentally" or purposefully going back using the basic android feature.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadUserProfile();
    }
}