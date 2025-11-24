package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.os.Bundle;
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
        dbHelper = new DatabaseHelper();

        //GUI
        et_j_username = findViewById(R.id.et_v_main_username);
        et_j_password = findViewById(R.id.et_v_main_password);
        btn_j_login = findViewById(R.id.btn_v_main_login);
        btn_j_createAccount = findViewById(R.id.btn_v_main_createAccount);

        

    }
}