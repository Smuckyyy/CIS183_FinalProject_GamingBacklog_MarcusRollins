package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class GameViewerActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    GameAdapter adapter;
    TabLayout tab_j_status;
    ListView lv_j_gameViewer;
    TextView tabProfile, tabGames, tabCommunity;
    ArrayList<GameInfo> gameInfoList;
    int currentUserId; //pass this from ProfileActivity when starting this activity

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_viewer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Get current userId from intent
        currentUserId = getIntent().getIntExtra("userId", -1);
        //Log.d("GameViewer", "Loaded userId = " + currentUserId);


        //Database
        dbHelper = new DatabaseHelper(this);

        //GUI
        lv_j_gameViewer = findViewById(R.id.lv_v_gameview_listOfGames);
        gameInfoList = new ArrayList<>();

        //TabLayout for Status
        tab_j_status = findViewById(R.id.tab_v_gameview_status);

        //Bottom tab references
        tabProfile = findViewById(R.id.tab_profile);
        tabGames = findViewById(R.id.tab_games);
        tabCommunity = findViewById(R.id.tab_community);


        setupTabLayout();
        populateGameList();
        setupBottomTabs();
    }


    private void setupBottomTabs()
    {

        tabProfile.setOnClickListener(v ->
        {
            Intent intent = new Intent(GameViewerActivity.this, ProfileActivity.class);
            intent.putExtra("userId", currentUserId);
            startActivity(intent);
        });

        tabGames.setOnClickListener(v ->
        {
            //Nothing goes here since we're already on the GameViewer
        });

        tabCommunity.setOnClickListener(v ->
        {
            Intent intent = new Intent(GameViewerActivity.this, CommunityActivity.class);
            intent.putExtra("userId", currentUserId);
            startActivity(intent);
        });
    }

    private void setupTabLayout()
    {
        //Load initial tab (Main/Rotation)
        loadGamesByStatus("main");


        tab_j_status.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        populateGameList(); //Main/Rotation
                        break;
                    case 1:
                        populateBackloggedList(); //Backlogged
                        break;
                    case 2:
                        populateCompletedList(); //Completed
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadGamesByStatus(String status)
    {
        gameInfoList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT g.gameName, s.systemName, ug.earnedXp " + "FROM UserGames ug " + "JOIN Games g ON ug.gameId = g.gameId " + "JOIN Systems s ON g.systemId = s.systemId " + "WHERE ug.userId = ? AND ug.status = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(currentUserId), status});

        while(cursor.moveToNext())
        {
            String gameName = cursor.getString(0);
            String systemName = cursor.getString(1);
            int xp = cursor.getInt(2);

            gameInfoList.add(new GameInfo(gameName, systemName, status, xp));
        }

        cursor.close();

        adapter = new GameAdapter(this, gameInfoList);
        lv_j_gameViewer.setAdapter(adapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        int selected = tab_j_status.getSelectedTabPosition();

        switch (selected) {
            case 0:
                populateGameList(); //Main/Rotation
                break;
            case 1:
                populateBackloggedList(); //Backlogged
                break;
            case 2:
                populateCompletedList(); //Completed
                break;
        }
    }


    private void populateGameList()
    {
        gameInfoList.clear();

        Cursor cursor = dbHelper.getGamesByStatus(currentUserId, "main");
        if (cursor != null && cursor.moveToFirst()) {
            do
            {
                String gameName = cursor.getString(0);
                String systemName = cursor.getString(1);
                String status = cursor.getString(2);
                int xp = cursor.getInt(3);
                gameInfoList.add(new GameInfo(gameName, systemName, status, xp));
            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new GameAdapter(this, gameInfoList);
        lv_j_gameViewer.setAdapter(adapter);

        Log.d("GameViewer", "Games loaded: " + gameInfoList.size());
    }

    private void populateCompletedList()
    {
        gameInfoList.clear();

        Cursor cursor = dbHelper.getGamesByStatus(currentUserId, "completed");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String gameName = cursor.getString(0);
                String systemName = cursor.getString(1);
                String status = cursor.getString(2);
                int xp = cursor.getInt(3);

                gameInfoList.add(new GameInfo(gameName, systemName, status, xp));
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

    private void populateBackloggedList()
    {
        gameInfoList.clear();

        Cursor cursor = dbHelper.getGamesByStatus(currentUserId, "backlogged");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String gameName = cursor.getString(0);
                String systemName = cursor.getString(1);
                String status = cursor.getString(2);
                int xp = cursor.getInt(3);

                gameInfoList.add(new GameInfo(gameName, systemName, status, xp));
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

}