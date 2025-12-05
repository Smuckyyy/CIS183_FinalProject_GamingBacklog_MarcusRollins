package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import static android.app.ProgressDialog.show;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccount extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    EditText et_j_username;
    EditText et_j_password;
    EditText et_j_confirmPassword;
    Button btn_j_createAccount;
    Button btn_j_back;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Database
        dbHelper = new DatabaseHelper(this); //***Might need to add something after this***

        //GUI
        et_j_username = findViewById(R.id.et_v_create_username);
        et_j_password = findViewById(R.id.et_v_create_password);
        et_j_confirmPassword = findViewById(R.id.et_v_create_confirmPass);
        btn_j_createAccount = findViewById(R.id.btn_v_create_createAccount);
        btn_j_back = findViewById(R.id.btn_v_create_back);

        buttonClickListener();
    }


    private void createAccount()
    {
        String username = et_j_username.getText().toString().trim();
        String password = et_j_password.getText().toString().trim();
        String confirmPassword = et_j_confirmPassword.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        {
            Toast.makeText(this, "All fields need to be entered.", Toast.LENGTH_SHORT).show();
            return; //Stop here
        }

        if(!password.equals(confirmPassword))
        {
            Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show();
            return; //Stop here
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Check if username exists
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE username = ?", new String[]{username});

        if(cursor.moveToFirst())
        {
            cursor.close();
            Toast.makeText(this, "Username already exists.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Stop cursor from going through
        cursor.close();

        //Put user in database
        String insertSQL = "INSERT INTO Users (username, password, totalXp) VALUES (?, ?, ?);";
        db.execSQL(insertSQL, new Object[]{username, password, 0});

        Toast.makeText(this, "Account Created, can now Login", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void buttonClickListener()
    {
        //Create the account and save to the database
        btn_j_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createAccount();
            }
        });

        //Go back to the MainActivity(Login)
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