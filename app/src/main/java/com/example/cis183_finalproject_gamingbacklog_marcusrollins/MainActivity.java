package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    EditText et_j_username;
    EditText et_j_password;
    Button btn_j_login;
    Button btn_j_createAccount;


    //----------------------------------------------------------------------------------------------------------------------
    //MAIN IS THE LOGIN INTENT. ANYTHING NEEDED TO LOGIN WILL BE FROM HERE
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Database
        dbHelper = new DatabaseHelper(this);
        //This opens the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Debugging Dummy Data
//        dbHelper.debugPrintUsers(db);
//        dbHelper.debugPrintSystems(db);
//        dbHelper.debugPrintGames(db);
//        dbHelper.debugPrintUserGames(db);

        //GUI

        et_j_username = findViewById(R.id.et_v_main_username);
        et_j_password = findViewById(R.id.et_v_main_password);
        btn_j_login = findViewById(R.id.btn_v_main_login);
        btn_j_createAccount = findViewById(R.id.btn_v_main_createAccount);

        buttonCallListener();

    }

    private void buttonCallListener()
    {
        btn_j_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent createAccountIntent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(createAccountIntent);
            }
        });

        btn_j_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = et_j_username.getText().toString().trim();
                String password = et_j_password.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty())
                {
                    et_j_password.setError("Please enter both fields");
                    return;
                }

                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT userId FROM Users WHERE username = ? AND password = ?", new String[]{username, password});

                if (cursor.moveToFirst())
                {
                    //Logged in — get userId
                    int userId = cursor.getInt(0);
                    cursor.close();

                    //Send to profile
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
                else
                {
                    cursor.close();
                    et_j_password.setError("Invalid username or password");
                }
            }
        });
    }
}