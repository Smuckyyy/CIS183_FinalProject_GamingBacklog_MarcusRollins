package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String database_name = "GamingBacklog.db";
    private static final String users_table_name = "Users";
    private static final String games_table_name = "Games";
    private static final String systems_table_name = "Systems";
    private static final String userGames_table_name = "UserGames";

    public DatabaseHelper(Context c)
    {
        //CHANGE VERSION NAME IF MAKING EDITS TO THE DATABASE AFTER RUNNING
        super(c, database_name, null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }


}
