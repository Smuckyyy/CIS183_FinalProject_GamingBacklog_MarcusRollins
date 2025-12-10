package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String database_name = "GamingBacklog.db";
    private static final String users_table = "Users";
    private static final String games_table = "Games";
    private static final String systems_table = "Systems";
    private static final String userGames_table = "UserGames";

    public DatabaseHelper(Context c)
    {
        //CHANGE VERSION NAME IF MAKING EDITS TO THE DATABASE AFTER RUNNING
        super(c, database_name, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + users_table + " (" + "userId integer primary key autoincrement not null, " + "username varchar(20), " + "password varchar(20), " + "totalXp integer default 0);");
        db.execSQL("CREATE TABLE " + systems_table + " (" + "systemId integer primary key autoincrement not null, " + "systemName varchar(20) not null" + ");");
        db.execSQL("CREATE TABLE " + games_table + " (" + "gameId integer primary key autoincrement not null, " + "gameName varchar(20), " + "systemId INTEGER, foreign key(systemId) references " + systems_table + "(systemId));");
        db.execSQL("CREATE TABLE " + userGames_table + " (" + "userGameId integer primary key autoincrement not null, " + "userId integer, " + "gameId integer, " + "status text not null, " + "earnedXp integer default 0, " + "foreign key(userId) references Users(userId), " + "foreign key(gameId) references Games(gameId));");

        //DUMMY DATA
        //Users
        db.execSQL("INSERT INTO Users (username, password, totalXp) VALUES ('MadiChoni', 'roxy', 0);");
        db.execSQL("INSERT INTO Users (username, password, totalXp) VALUES ('NukeNasty', 'hogan', 0);");

        //Systems
        db.execSQL("INSERT INTO Systems (systemName) VALUES ('PlayStation');");
        db.execSQL("INSERT INTO Systems (systemName) VALUES ('Xbox');");
        db.execSQL("INSERT INTO Systems (systemName) VALUES ('Switch');");

        //Games
        db.execSQL("INSERT INTO Games (gameName, systemId) VALUES ('God of War', 1);");
        db.execSQL("INSERT INTO Games (gameName, systemId) VALUES ('Halo Infinite', 2);");
        db.execSQL("INSERT INTO Games (gameName, systemId) VALUES ('Zelda TOTK', 3);");

        //UserGames
        db.execSQL("INSERT INTO UserGames (userId, gameId, status, earnedXp) VALUES (1, 1, 'main', 120);");
        db.execSQL("INSERT INTO UserGames (userId, gameId, status, earnedXp) VALUES (1, 3, 'completed', 300);");
        db.execSQL("INSERT INTO UserGames (userId, gameId, status, earnedXp) VALUES (2, 2, 'backlogged', 0);");

    }

    //This can be changed later on to ALTER table instead of DROP. DROP will delete any users created and ALTER will just allow the version change to go through without deleting anything new
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop all tables if database version changes
        db.execSQL("DROP TABLE IF EXISTS " + userGames_table);
        db.execSQL("DROP TABLE IF EXISTS " + games_table);
        db.execSQL("DROP TABLE IF EXISTS " + systems_table);
        db.execSQL("DROP TABLE IF EXISTS " + users_table);

        //Recreate tables
        onCreate(db);
    }


    //This is for debug and showing dummy data
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Show users
    public void debugPrintUsers(SQLiteDatabase db)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);

        while (cursor.moveToNext())
        {
            Log.d("DB_DEBUG", "UserID: " + cursor.getInt(0) + " | Username: " + cursor.getString(1) + " | XP: " + cursor.getInt(3));
        }

        cursor.close();
    }

    //Show systems
    public void debugPrintSystems(SQLiteDatabase db)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM Systems", null);

        while (cursor.moveToNext())
        {
            Log.d("DB_DEBUG", "SystemID: " + cursor.getInt(0) + " | SystemName: " + cursor.getString(1));
        }

        cursor.close();
    }

    //Show games
    public void debugPrintGames(SQLiteDatabase db)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM Games", null);

        while (cursor.moveToNext())
        {
            Log.d("DB_DEBUG", "GameID: " + cursor.getInt(0) + " | GameName: " + cursor.getString(1) + " | SystemID: " + cursor.getInt(2));
        }

        cursor.close();
    }

    //Show UserGames(Community Tab)
    public void debugPrintUserGames(SQLiteDatabase db)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM UserGames", null);

        while (cursor.moveToNext())
        {
            Log.d("DB_DEBUG", "UserGameID: " + cursor.getInt(0) + " | UserID: " + cursor.getInt(1) + " | GameID: " + cursor.getInt(2) + " | Status: " + cursor.getString(3) + " | EarnedXP: " + cursor.getInt(4));
        }

        cursor.close();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public Cursor getGamesByStatus(int userId, String status)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT g.gameName, s.systemName, ug.status, ug.earnedXp " + "FROM UserGames ug " + "INNER JOIN Games g ON ug.gameId = g.gameId " + "INNER JOIN Systems s ON g.systemId = s.systemId " + "WHERE ug.userId = ? AND ug.status = ?";

        return db.rawQuery(query, new String[]{ String.valueOf(userId), status });
    }

    public Cursor getAllSystems()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT systemId, systemName FROM Systems", null);
    }

    public void addSystem(String systemName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Systems (systemName) VALUES (?);", new Object[]{ systemName });

        db.close();
    }


}
