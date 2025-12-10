package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

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

public class AddSystemDatabaseActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    EditText et_j_systemName;
    Button btn_j_addSystem;
    Button btn_j_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_system_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Database
        dbHelper = new DatabaseHelper(this);

        //GUI
        et_j_systemName = findViewById(R.id.et_v_addsystemdb_systemName);
        btn_j_addSystem = findViewById(R.id.btn_v_addsystemdb_addSystem);
        btn_j_back = findViewById(R.id.btn_v_addsystemdb_back);

        buttonCallListeners();
    }

    private void buttonCallListeners()
    {
        //Add system
        btn_j_addSystem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = et_j_systemName.getText().toString().trim();

                if(name.isEmpty())
                {
                    et_j_systemName.setError("System name required");
                    return;
                }

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String sql = "INSERT INTO Systems (systemName) VALUES (?)";
                db.execSQL(sql, new Object[]{et_j_systemName});

                Toast.makeText(AddSystemDatabaseActivity.this, "System added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Back
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